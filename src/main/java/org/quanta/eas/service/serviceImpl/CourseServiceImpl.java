package org.quanta.eas.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.quanta.eas.constants.RedisPrefix;
import org.quanta.eas.dto.AddCourseDTO;
import org.quanta.eas.dto.CourseYearDO;
import org.quanta.eas.dto.EditCourseDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.*;
import org.quanta.eas.entity.Class;
import org.quanta.eas.exception.BusinessException;
import org.quanta.eas.exception.PermissionException;
import org.quanta.eas.mapper.*;
import org.quanta.eas.pojo.*;
import org.quanta.eas.service.CourseMajorYearService;
import org.quanta.eas.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quanta.eas.utils.PageResult;
import org.quanta.eas.utils.RedisUtils;
import org.quanta.eas.vo.ClassInfoVO;
import org.quanta.eas.vo.ClassTableVO;
import org.quanta.eas.vo.CourseInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wzf mqh hzq zjx
 * @since 2022-12-10
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseMajorMapper courseMajorMapper;
    @Autowired
    private CourseMajorYearMapper courseMajorYearMapper;
    @Autowired
    private MajorMapper majorMapper;
    @Autowired
    private CourseMajorYearService courseMajorYearService;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private CourseClassMapper courseClassMapper;
    @Autowired
    private ClassTimeMapper classTimeMapper;
    @Autowired
    private ClassStudentMapper classStudentMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    @Transactional
    public void addCourse(AddCourseDTO param) {
        // 先保存课程信息
        Course course = new Course();
        course.setMajorId(param.getMajorId());
        course.setName(param.getName());
        course.setNumber(param.getNumber());
        save(course);

        // 学院id
        List<Long> majorId = param.getAllow().stream().map(CourseYearDO::getMajorId).collect(Collectors.toList());
        Long count = majorMapper.selectCount(new LambdaUpdateWrapper<Major>().in(Major::getId, majorId));
        if (count != majorId.size()) {
            throw new BusinessException("存在非法学院数据");
        }

        // 插入学院和月年信息
        param.getAllow().forEach(e -> {
            org.quanta.eas.entity.CourseMajor courseMajor = new org.quanta.eas.entity.CourseMajor(course.getId(), e.getMajorId());
            courseMajorMapper.insert(courseMajor);
            List<CourseMajorYear> courseYearList = e.getYear().stream().map(i -> new CourseMajorYear(courseMajor.getId(), i)).collect(Collectors.toList());
            // 插入年级信息
            courseMajorYearService.saveBatch(courseYearList);
        });

    }

    /**
     * 获取课程列表
     */
    @Override
    public PageResult getCourseList(PageParamDTO param, String keyWord) {
        // 查询全部课程
        List<CourseInfo> courseList = courseMapper.getCoursePage(keyWord, param.getStart(), param.getPageSize());
        Long total = courseMapper.getCoursePageCount(keyWord);
        return new PageResult(courseList, total, param.getPageSize());
    }

    /**
     * 获取课程详情
     *
     * @param id
     */
    @Override
    public CourseInfoVO getCourse(Long id) {
        // 获取课程基本信息
        CourseInfo course = courseMapper.getCourse(id);
        // 获取课程可报名学院信息
        List<CourseMajorInfo> courseMajor = courseMajorMapper.getCourseMajor(id);
        // 获取课程班级列表
        List<ClassInfo> classInfoVOList = classMapper.getCourseClass(id);
        classInfoVOList.forEach(e -> {
            // 获取班级剩余容量
            e.setSurplus(redisUtils.getIntOrZero(String.format(RedisPrefix.CLASS_CAPACITY_PREFIX, e.getId())));
        });
        return new CourseInfoVO(course, courseMajor, classInfoVOList);
    }

    @Override
    public CourseInfoVO getCourse(Long curseId, Long teacherId) {
        // 获取课程班级列表
        List<ClassInfo> classInfoVOList = classMapper.getTeacherCourseClass(curseId, teacherId);
        // 如果获取不到课程班级信息 说明教师为非授课教师 (非法访问)
        if (classInfoVOList.isEmpty()) {
            throw new PermissionException();
        }

        // 获取课程基本信息
        CourseInfo course = courseMapper.getCourse(curseId);
        // 获取课程可报名学院信息
        List<CourseMajorInfo> courseMajor = courseMajorMapper.getCourseMajor(curseId);
        return new CourseInfoVO(course, courseMajor, classInfoVOList);
    }


    /**
     * 删除课程
     */
    @Override
    @Transactional
    public void deleteCourse(Long id) {
        // 先删除课程
        int res = courseMapper.deleteById(id);
        if (res != 1) {
            throw new BusinessException("删除课程失败");
        }

        // 查询可选课学院列表id
        List<CourseMajor> courseMajorList = courseMajorMapper.selectList(
                new LambdaQueryWrapper<CourseMajor>()
                        .eq(CourseMajor::getCourseId, id)
                        .select(CourseMajor::getId)
        );
        if (!courseMajorList.isEmpty()) {
            List<Long> ids = courseMajorList.stream().map(CourseMajor::getId).collect(Collectors.toList());
            // 删除可选课学院信息
            courseMajorMapper.delete(new LambdaUpdateWrapper<CourseMajor>().eq(CourseMajor::getCourseId, id));
            // 删除可选可年级信息
            courseMajorYearMapper.delete(
                    new LambdaUpdateWrapper<CourseMajorYear>()
                            .in(CourseMajorYear::getCourseMajorId, ids)
            );
        }

        // 查询班级信息
        List<CourseClass> courseClassList = courseClassMapper.selectList(
                new LambdaQueryWrapper<CourseClass>()
                        .eq(CourseClass::getCourseId, id)
                        .select(CourseClass::getClassId)
        );

        if (!courseClassList.isEmpty()) {
            // 删除课程 => 班级关联信息
            courseClassMapper.delete(
                    new LambdaUpdateWrapper<CourseClass>()
                            .eq(CourseClass::getCourseId, id)
            );
            // 删除班级信息
            // 先获取班级id列表
            // 获取要删除的课程班级关联表id
            List<Long> courseClassIds = courseClassList.stream().map(CourseClass::getId).collect(Collectors.toList());
            // 删除班级信息
            classMapper.delete(
                    new LambdaUpdateWrapper<Class>()
                            .in(Class::getId, courseClassIds)
            );

            // 删除班级时间信息
            classTimeMapper.delete(
                    new LambdaUpdateWrapper<ClassTime>()
                            .in(ClassTime::getClassId, courseClassIds)
            );

            // 删除课程学生信息
            classStudentMapper.delete(
                    new LambdaUpdateWrapper<ClassStudent>()
                            .in(ClassStudent::getClassId, courseClassIds)
            );
        }
    }

    /**
     * 查询学生可选课程列表
     */
    @Override
    public PageResult getStudentCourseList(long uid, String keyWord, String term, PageParamDTO param) {
        List<CourseClassInfo> studentCourseList = courseMapper.getStudentCourseList(uid, keyWord, term, param.getStart(), param.getPageSize());
        studentCourseList.forEach(c -> {
            c.getClassList().forEach(cl -> {
                cl.setSurplus(redisUtils.getIntOrZero(String.format(RedisPrefix.CLASS_CAPACITY_PREFIX, cl.getId())));
            });
        });
        Long total = courseMapper.getStudentCourseCount(uid, keyWord, term);
        return new PageResult(studentCourseList, total, param.getPageSize());
    }

    /**
     * 判断学生是否选课成功
     */
    @Override
    @Transactional
    public void choiceCourse(Long cid, Long sid) {
        // 先查询班级对应课程id
        CourseClass courseClass = courseClassMapper.selectOne(new LambdaQueryWrapper<CourseClass>().eq(CourseClass::getClassId, cid).select(CourseClass::getCourseId));
        if (courseClass == null) {
            throw new BusinessException("课程不存在");
        }

        // 通过课程id查询对应班级列表
        List<Long> classIdList = courseClassMapper.selectList(
                new LambdaQueryWrapper<CourseClass>()
                        .eq(CourseClass::getCourseId, courseClass.getCourseId())
        ).stream().map(CourseClass::getClassId).collect(Collectors.toList());

        // 通过班级id和学生id查询学生是否已选该课程
        Long count = classStudentMapper.selectCount(
                new LambdaUpdateWrapper<ClassStudent>()
                        .in(ClassStudent::getClassId, classIdList)
                        .eq(ClassStudent::getStudentId, sid)
        );
        if (count != 0) {
            throw new BusinessException("已选该课程");
        }

        // 查询学生已选课程时间列表
        List<ClassStudent> classStudents = classStudentMapper.selectList(
                new LambdaQueryWrapper<ClassStudent>()
                        .eq(ClassStudent::getStudentId, sid)
        );

        // 若学生有选其他课程
        if (!classStudents.isEmpty()) {
            // 获取学生已选课程id
            List<Long> studentClassIdList = classStudents.stream().map(ClassStudent::getClassId).collect(Collectors.toList());
            // 查询当前课程id
            List<ClassTime> classTimeList1 = classTimeMapper.selectList(
                    new LambdaQueryWrapper<ClassTime>().eq(
                            ClassTime::getClassId, cid));
            // 查询课程时间是否冲突
            Long count1 = classTimeMapper.selectCount(
                    new LambdaQueryWrapper<ClassTime>()
                            .and(
                                    q -> q
                                            .in(ClassTime::getWeek, classTimeList1
                                                    .stream()
                                                    .map(ClassTime::getWeek)
                                                    .collect(Collectors.toList()))
                                            .in(ClassTime::getLesson, classTimeList1
                                                    .stream()
                                                    .map(ClassTime::getLesson)
                                                    .collect(Collectors.toList()))
                            ).in(ClassTime::getClassId, studentClassIdList));
            if (count1 != 0) {
                throw new BusinessException("选课时间冲突");
            }
        }

        // 先执行扣除容量操作
        long capacity = redisUtils.decr(String.format(RedisPrefix.CLASS_CAPACITY_PREFIX, cid));
        try {
            // 如果扣除后容量小于0 则说明容量不足
            if (capacity < 0) {
                redisUtils.set(String.format(RedisPrefix.CLASS_CAPACITY_PREFIX, cid), 0);
                throw new BusinessException("人数已满");
            }

            // 如果扣除后容量大于等于0 则插入学生选课信息
            classStudentMapper.insert(new ClassStudent(sid, cid));
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            // 程序运行错误时再回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            redisUtils.incr(String.format(RedisPrefix.CLASS_CAPACITY_PREFIX, cid));
            log.error(e.getMessage());
        }
    }

    /**
     * 取消课程
     */
    @Override
    @Transactional
    public void cancelChoiceCourse(Long cid, Long sid) {
        int count = classStudentMapper.delete(
                new LambdaQueryWrapper<ClassStudent>()
                        .eq(ClassStudent::getClassId, cid)
                        .eq(ClassStudent::getStudentId, sid)
        );
        if (count <= 0) {
            throw new BusinessException("删除失败");
        }
        // 返还课程空位
        redisUtils.incr(String.format(RedisPrefix.CLASS_CAPACITY_PREFIX, cid));
    }

    /**
     * 查询学生课表
     */
    @Override
    public ClassTableVO getStudentClassTable(Long uid, String term) {
        List<ClassInfoVO> classInfoVOList = classStudentMapper.getStudentClassTable(uid, term);
        if (!classInfoVOList.isEmpty()) {
            // 查询课程时间
            List<ClassTimeInfo> classTimeList = classTimeMapper.getClassTimeList(classInfoVOList
                    .stream()
                    .map(ClassInfoVO::getId)
                    .collect(Collectors.toList()));

            // 转换成map方便后续取值
            Map<Long, ClassTimeInfo> classTimeInfoMap = classTimeList
                    .stream()
                    .collect(Collectors
                            .toMap(ClassTimeInfo::getClassId, e -> e));

            classInfoVOList.forEach(e -> {
                e.setClassTime(classTimeInfoMap.get(e.getId()));
            });

        }
        return new ClassTableVO(classInfoVOList);
    }

    @Override
    public ClassTableVO getTeacherClassTable(Long uid, String term) {
        List<ClassInfoVO> classInfoVOList = classStudentMapper.getTeacherClassTable(uid, term);
        if (!classInfoVOList.isEmpty()) {
            // 查询课程时间
            List<ClassTimeInfo> classTimeList = classTimeMapper.getClassTimeList(classInfoVOList
                    .stream()
                    .map(ClassInfoVO::getId)
                    .collect(Collectors.toList()));

            // 转换成map方便后续取值
            Map<Long, ClassTimeInfo> classTimeInfoMap = classTimeList
                    .stream()
                    .collect(Collectors
                            .toMap(ClassTimeInfo::getClassId, e -> e));

            classInfoVOList.forEach(e -> {
                e.setClassTime(classTimeInfoMap.get(e.getId()));
            });

        }
        return new ClassTableVO(classInfoVOList);
    }

    //    /**
//     * 查询学生课表
//     */
//    @Override
//    public ClassTableVO getClassTable(Long uid) {
//        // 查询课程id
//        List<ClassStudent> classStudents = classStudentMapper.selectList(
//                new LambdaQueryWrapper<ClassStudent>().eq(ClassStudent::getStudentId, uid)
//        );
//        if (classStudents.isEmpty()) {
//            return new ClassTableVO();
//        }
//        // 查询课程信息
//        List<Class> classList = classMapper.selectList(
//                new LambdaQueryWrapper<Class>()
//                        .in(Class::getId, classStudents
//                                .stream()
//                                .map(ClassStudent::getClassId)
//                                .collect(Collectors.toList()))
//        );
//        // 构造返回结果
//        List<ClassInfoVO> classInfoVOList = new ArrayList<>();
//        if (!classList.isEmpty()) {
//            // 查询课程时间
//            List<ClassTimeInfo> classTimeList = classTimeMapper.getClassTimeList(classList
//                    .stream()
//                    .map(Class::getId)
//                    .collect(Collectors.toList()));
//
//            // 转换成map方便后续取值
//            Map<Long, ClassTimeInfo> classTimeInfoMap = classTimeList
//                    .stream()
//                    .collect(Collectors
//                            .toMap(ClassTimeInfo::getClassId, e -> e));
//
//            // 查询课程教师名称
//            Map<Long, String> teacherMap = teacherMapper.selectList(
//                    new LambdaQueryWrapper<Teacher>()
//                            .in(Teacher::getId, classList
//                                    .stream()
//                                    .map(Class::getTeacherId)
//                                    .collect(Collectors.toList()))
//            ).stream().collect(Collectors.toMap(Teacher::getId, Teacher::getName));
//
//
//            classList.forEach(e -> {
//                ClassInfoVO classInfoVO = new ClassInfoVO();
//                BeanUtils.copyProperties(e, classInfoVO);
//                classInfoVO.setClassTime(classTimeInfoMap.get(e.getId()));
//                classInfoVO.setTeacherName(teacherMap.get(e.getTeacherId()));
//                classInfoVOList.add(classInfoVO);
//            });
//        }
//        return new ClassTableVO(classInfoVOList);
//    }

    @Override
    public void editCourse(EditCourseDTO param) {
        // 查询课程是否存在
        Course course = courseMapper.selectById(param.getId());
        if (course == null) {
            throw new BusinessException("课程不存在");
        }

        if (param.getMajor() != null) {
            // 校验学院是否合法
            Major major = majorMapper.selectOne(
                    new LambdaQueryWrapper<Major>()
                            .eq(Major::getMajor, param.getMajor())
            );

            // 学院不存在
            if (major == null) {
                throw new BusinessException("学院不存在");
            }

            course.setMajorId(major.getId());
        }

        if (param.getName() != null) {
            course.setNumber(param.getName());
        }

        if (param.getNumber() != null) {
            course.setNumber(param.getNumber());
        }

        // 更新课程
        courseMapper.updateById(course);

    }

    /**
     * 获取学生已选课程列表
     */
    @Override
    public PageResult getStudentClassList(Long id, String term, String keyWord, PageParamDTO param) {
        List<ClassInfoVO> classInfoVOList = classStudentMapper.getStudentClassList(id, term, keyWord, param.getStart(), param.getPageSize());
        if (!classInfoVOList.isEmpty()) {
            // 查询课程时间
            List<ClassTimeInfo> classTimeList = classTimeMapper.getClassTimeList(classInfoVOList
                    .stream()
                    .map(ClassInfoVO::getId)
                    .collect(Collectors.toList()));

            // 转换成map方便后续取值
            Map<Long, ClassTimeInfo> classTimeInfoMap = classTimeList
                    .stream()
                    .collect(Collectors
                            .toMap(ClassTimeInfo::getClassId, e -> e));

            classInfoVOList.forEach(e -> {
                e.setClassTime(classTimeInfoMap.get(e.getId()));
                e.setSurplus(redisUtils.getIntOrZero(String.format(RedisPrefix.CLASS_CAPACITY_PREFIX, e.getId())));
            });

        }
        Long total = classStudentMapper.getStudentClassListCount(id, term, keyWord);
        return new PageResult(classInfoVOList, total, param.getPageSize());
    }
}

package org.quanta.eas.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.quanta.eas.constants.RedisPrefix;
import org.quanta.eas.dto.AddClassDTO;
import org.quanta.eas.dto.AddStudentToClassDTO;
import org.quanta.eas.dto.EditClassDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.*;
import org.quanta.eas.entity.Class;
import org.quanta.eas.exception.BusinessException;
import org.quanta.eas.exception.ParamException;
import org.quanta.eas.exception.PermissionException;
import org.quanta.eas.mapper.*;
import org.quanta.eas.pojo.CourseClassInfo;
import org.quanta.eas.service.ClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quanta.eas.service.ClassTimeService;
import org.quanta.eas.service.CourseService;
import org.quanta.eas.utils.PageResult;
import org.quanta.eas.utils.RedisUtils;
import org.quanta.eas.vo.ClassInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {

    @Autowired
    CourseMapper courseMapper;
    @Autowired
    ClassMapper classMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    ClassTimeService classTimeService;
    @Autowired
    ClassTimeMapper classTimeMapper;
    @Autowired
    CourseClassMapper courseClassMapper;
    @Autowired
    ClassStudentMapper classStudentMapper;
    @Autowired
    RedisUtils redisUtils;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    CourseService courseService;

    /**
     * 添加课程
     */
    @Override
    @Transactional
    public void addClass(AddClassDTO param) {
        // 校验参数
        if ((param.getUsualPercentage() + param.getFinalPercentage()) != 1.0) {
            throw new ParamException("请确保平时成绩百分比+最终成绩百分比之和为1");
        }

        // 先查询课程是否存在
        Course course = courseMapper.selectById(param.getCourseId());
        if (course == null) {
            throw new BusinessException("课程不存在");
        }

        // 查询教师是否存在
        Teacher teacher = teacherMapper.selectOne(new LambdaQueryWrapper<Teacher>().eq(Teacher::getNumber, param.getTeacherNumber()));
        if (teacher == null) {
            throw new BusinessException("授课教师不存在");
        } else {
            // 判断教师授课时间是否冲突
            List<Class> classList = classMapper.selectList(
                    new LambdaQueryWrapper<Class>()
                            .eq(Class::getTeacherId, teacher.getId())
                            .eq(Class::getTerm, param.getTerm())
            );
            // 教师教授的课程id列表
            List<Long> ids = classList.stream().map(Class::getId).collect(Collectors.toList());
            if (!classList.isEmpty()) {
                // 查询教师授课时间
                List<ClassTime> classTimeList = classTimeMapper.selectList(
                        new LambdaQueryWrapper<ClassTime>()
                                .in(ClassTime::getClassId, ids)
                );

                // 遍历教师授课时间列表
                classTimeList.forEach(ct -> {
                    param.getClassTime().getLesson().forEach(l -> {
                        if (ct.getWeek().equals(param.getClassTime().getWeek()) && ct.getLesson().equals(l)) {
                            throw new BusinessException("教师授课时间重复");
                        }
                    });
                });
            }
        }

        // 拷贝课程信息
        Class c = new Class();
        BeanUtils.copyProperties(param, c);
        c.setTeacherId(teacher.getId());
        // 设置默认值
        // 平时成绩百分比为60%
        if (c.getUsualPercentage() == null) {
            c.setUsualPercentage(0.6);
        }
        // 期末成绩40%
        if (c.getFinalPercentage() == null) {
            c.setFinalPercentage(0.4);
        }

        // 先插入课程信息
        classMapper.insert(c);

        // 插入课程和班级关联信息
        courseClassMapper.insert(new CourseClass(param.getCourseId(), c.getId()));

        // 然后插入上课时间信息
        List<ClassTime> classTimeList = param.getClassTime().getLesson()
                .stream().map(e -> new ClassTime(c.getId(), param.getClassTime().getWeek(), e)).collect(Collectors.toList());

        // 插入课程上课时间信息
        classTimeService.saveBatch(classTimeList);

        // redis保存课程人数
        redisUtils.set(String.format(RedisPrefix.CLASS_CAPACITY_PREFIX, c.getId()), c.getCapacity());
    }

    /**
     * 删除班级
     */
    @Override
    @Transactional
    public void deleteClass(Long id) {
        // 先删除班级信息
        int res = classMapper.deleteById(id);
        if (res != 1) {
            throw new BusinessException("删除班级失败");
        }

        // 删除课程和班级的联系
        courseClassMapper.delete(
                new LambdaQueryWrapper<CourseClass>()
                        .eq(CourseClass::getClassId, id)
        );

        // 删除班级上课时间
        classTimeMapper.delete(
                new LambdaQueryWrapper<ClassTime>()
                        .eq(ClassTime::getClassId, id)
        );

        // 删除班级学生
        classStudentMapper.delete(
                new LambdaQueryWrapper<ClassStudent>()
                        .eq(ClassStudent::getClassId, id)
        );
    }

    /**
     * 获取班级信息
     */
    @Override
    public ClassInfoVO getClassInfo(Long id) {
        // 先查询班级信息
        ClassInfoVO classInfo = classMapper.getClassInfo(id);
        if (classInfo == null) {
            throw new BusinessException("班级不存在");
        }
        // 查询班级上课时间信息
        classInfo.setClassTime(classTimeMapper.getClassTime(id));

        // 查询班级学生信息
        classInfo.setStudentList(classStudentMapper.getClassStudentInfo(id));
        classInfo.getStudentList().forEach(e -> {
            // 计算最终成绩
            e.setScore(e.getUsualScore() * classInfo.getUsualPercentage()
                    + e.getFinalScore() * classInfo.getFinalPercentage());
        });
        // 获取班级剩余容量
        classInfo.setSurplus(redisUtils.getIntOrZero(String.format(RedisPrefix.CLASS_CAPACITY_PREFIX, id)));
        return classInfo;
    }

    @Override
    public ClassInfoVO getClassInfo(Long classId, Long teacherId) {
        // 先查询班级信息
        ClassInfoVO classInfo = classMapper.getTeacherClassInfo(classId, teacherId);
        if (classInfo == null) {
            throw new PermissionException();
        }

        // 查询班级上课时间信息
        classInfo.setClassTime(classTimeMapper.getClassTime(classId));

        // 查询班级学生信息
        classInfo.setStudentList(classStudentMapper.getClassStudentInfo(classId));
        classInfo.getStudentList().forEach(e -> {
            // 计算最终成绩
            e.setScore(e.getUsualScore() * classInfo.getUsualPercentage()
                    + e.getFinalScore() * classInfo.getFinalPercentage());
        });
        // 获取班级剩余容量
        classInfo.setSurplus(redisUtils.getIntOrZero(String.format(RedisPrefix.CLASS_CAPACITY_PREFIX, classId)));
        return classInfo;
    }

    /**
     * 获取教师课程列表
     */
    @Override
    public PageResult getTeacherCourseClassList(Long id, String term, String keyWord, PageParamDTO param) {
        List<CourseClassInfo> data = courseMapper.getCourseClassByTeacherId(id, term,keyWord, param.getStart(), param.getPageSize());
        Long total = courseMapper.getCourseClassByTeacherIdCount(id, term,keyWord);
        return new PageResult(data, total, param.getPageSize());
    }

    /**
     * 将学生添加到班级中
     */
    @Override
    public void addStudent2Class(AddStudentToClassDTO param) {
        Student student = studentMapper.selectOne(
                new LambdaQueryWrapper<Student>()
                        .eq(Student::getNumber, param.getNumber())
        );
        if (student == null) {
            throw new BusinessException("学生不存在");
        }
        // 选择课程
        courseService.choiceCourse(param.getClassId(), student.getId());
    }

    /**
     * 编辑班级信息
     */
    @Override
    public void editClass(EditClassDTO param) {
        Class c = classMapper.selectById(param.getId());
        if (c == null) {
            throw new BusinessException("班级不存在");
        }
        // 验证班级人数
        if (param.getCapacity() != null) {
            int currentCapacity = (int) redisUtils.getIntOrZero(String.format(RedisPrefix.CLASS_CAPACITY_PREFIX, param.getId()));
            if (currentCapacity > param.getCapacity()) {
                throw new BusinessException("不可将课程人数减少到已选课人数以下");
            }
        }

        BeanUtils.copyProperties(param, c);
        // 编辑授课教师
        if (param.getNumber() != null && !param.getNumber().equals("")) {
            Teacher teacher = teacherMapper.selectOne(
                    new LambdaQueryWrapper<Teacher>()
                            .eq(Teacher::getNumber, param.getNumber())
            );

            // 判断教师授课时间是否冲突
            List<Class> classList = classMapper.selectList(
                    new LambdaQueryWrapper<Class>()
                            .eq(Class::getTeacherId, teacher.getId())
                            .eq(Class::getTerm, param.getTerm())
            );
            // 教师教授的课程id列表
            List<Long> ids = classList.stream().map(Class::getId).collect(Collectors.toList());
            if (!classList.isEmpty()) {
                // 查询教师授课时间
                List<ClassTime> classTimeList = classTimeMapper.selectList(
                        new LambdaQueryWrapper<ClassTime>()
                                .in(ClassTime::getClassId, ids)
                );
                // 查询当前班级授课时间
                List<ClassTime> currentClassTimeList = classTimeMapper.selectList(
                        new LambdaQueryWrapper<ClassTime>()
                                .eq(ClassTime::getClassId, param.getId())
                );

                // 遍历教师授课时间列表
                classTimeList.forEach(ct -> {
                    currentClassTimeList.forEach(cct -> {
                        if (ct.getWeek().equals(cct.getWeek()) && ct.getLesson().equals(cct.getLesson())) {
                            throw new BusinessException("教师授课时间重复");
                        }
                    });
                });
            }
            c.setTeacherId(teacher.getId());
        }
        classMapper.updateById(c);
    }
}

package org.quanta.eas.service;

import org.quanta.eas.dto.AddCourseDTO;
import org.quanta.eas.dto.EditCourseDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quanta.eas.utils.PageResult;
import org.quanta.eas.vo.ClassTableVO;
import org.quanta.eas.vo.CourseInfoVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wzf mqh hzq zjx
 * @since 2022-12-10
 */
public interface CourseService extends IService<Course> {
    // 添加课程
    public void addCourse(AddCourseDTO param);

    // 分页获取课程列表
    public PageResult getCourseList(PageParamDTO param, String keyWord);

    // 获取课程详情
    public CourseInfoVO getCourse(Long id);

    // 获取课程中教师教授的班级
    public CourseInfoVO getCourse(Long curseId, Long teacherId);

    // 删除课程
    public void deleteCourse(Long id);

    // 学生获取可选课程列表
    public PageResult getStudentCourseList(long uid,String keyWord, String term, PageParamDTO param);

    // 学生选课
    public void choiceCourse(Long cid, Long sid);

    // 取消选课
    public void cancelChoiceCourse(Long cid, Long sid);

    // 获取学生课程列表
    public ClassTableVO getStudentClassTable(Long uid, String term);

    // 获取教师课表
    public ClassTableVO getTeacherClassTable(Long uid, String term);

    // 编辑课程
    public void editCourse(EditCourseDTO param);

    // 获取学生已选课程列表
    public PageResult getStudentClassList(Long id, String term, String keyWord, PageParamDTO param);
}

package org.quanta.eas.mapper;

import org.quanta.eas.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.eas.pojo.CourseClassInfo;
import org.quanta.eas.pojo.CourseInfo;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wzf mqh hzq zjx
 * @since 2022-12-10
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    // 通过教师id查询课程总数
    Long getCoursePageByTeacherIdCount(Long teacherId);

    // 分页获取课程信息
    List<CourseInfo> getCoursePage(String keyWord,Integer start, Integer size);

    // 获取课程总数
    Long getCoursePageCount(String keyWord);

    // 获取课程
    CourseInfo getCourse(Long id);

    // 通过教师id查询课程和班级列表
    List<CourseClassInfo> getCourseClassByTeacherId(Long id, String term, String keyWord, int start, int size);

    // 根据教师id获取课程和班级列表总数
    Long getCourseClassByTeacherIdCount(Long id, String term, String keyWord);

    // 通过课程id和教师id查询对应班级
    Long getCourseClassByTeacherIdAndCourseIdCount(Long courseId, Long teacherId);

    // 分页获取学生可选课列表
    List<CourseClassInfo> getStudentCourseList(Long id, String keyWord, String term, int start, int size);

    // 获取学生可选课程总数
    Long getStudentCourseCount(Long id, String keyWord, String term);
}

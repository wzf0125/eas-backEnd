package org.quanta.eas.mapper;

import org.quanta.eas.entity.Class;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.eas.pojo.ClassInfo;
import org.quanta.eas.vo.ClassInfoVO;

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
public interface ClassMapper extends BaseMapper<Class> {
    // 获取课程班级列表
    public List<ClassInfo> getCourseClass(Long id);

    // 获取课程授课教师班级列表
    public List<ClassInfo> getTeacherCourseClass(Long courseId, Long teacherId);

    // 获取班级信息
    public ClassInfoVO getClassInfo(Long id);

    // 获取教师教授的班级详情
    public ClassInfoVO getTeacherClassInfo(Long classId, Long teacherId);
}

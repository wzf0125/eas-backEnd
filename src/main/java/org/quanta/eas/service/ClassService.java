package org.quanta.eas.service;

import org.quanta.eas.dto.AddClassDTO;
import org.quanta.eas.dto.AddStudentToClassDTO;
import org.quanta.eas.dto.EditClassDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.Class;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quanta.eas.utils.PageResult;
import org.quanta.eas.vo.ClassInfoVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wzf mqh hzq zjx
 * @since 2022-12-10
 */
public interface ClassService extends IService<Class> {
    // 添加课程
    public void addClass(AddClassDTO param);

    // 删除班级
    public void deleteClass(Long id);

    // 获取班级详情
    public ClassInfoVO getClassInfo(Long id);

    // 获取教师教授的班级详情
    public ClassInfoVO getClassInfo(Long classId, Long teacherId);

    // 获取教师课程列表
    public PageResult getTeacherCourseClassList(Long id, String term,String keyWord,PageParamDTO param);

    void addStudent2Class(AddStudentToClassDTO param);

    // 编辑班级信息
    void editClass(EditClassDTO param);
}

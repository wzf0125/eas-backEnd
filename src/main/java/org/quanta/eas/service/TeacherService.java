package org.quanta.eas.service;

import org.quanta.eas.dto.AddTeacherDTO;
import org.quanta.eas.dto.EditTeacherDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quanta.eas.utils.PageResult;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wzf mqh hzq zjx
 * @since 2022-12-10
 */
public interface TeacherService extends IService<Teacher> {
    // 分页查询教师
    public PageResult getTeacherList(PageParamDTO page,String keyWord);

    // 编辑教师信息
    public void editTeacher(EditTeacherDTO data);

    // 删除教师
    public void deleteTeacher(Long id);

    // 添加教师信息
    public void addTeacher(AddTeacherDTO teacher);

    // 通过学号验证教师是否存在方法
    public boolean checkTeacherIsExistByNumber(String number);

    // 查询学生列表
    PageResult getMajorTeacherList(Long id, String keyWord, PageParamDTO param);
}

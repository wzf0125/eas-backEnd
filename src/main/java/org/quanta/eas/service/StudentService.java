package org.quanta.eas.service;

import org.quanta.eas.dto.AddStudentDTO;
import org.quanta.eas.dto.EditStudentDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quanta.eas.utils.PageResult;
import org.quanta.eas.vo.StudentInfoVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wzf mqh hzq zjx
 * @since 2022-12-10
 */
public interface StudentService extends IService<Student> {
    // 获取学生信息
    public PageResult getStudentList(String keyWord,PageParamDTO data);

    // 更新学生信息
    public void editStudent(EditStudentDTO editStudentDTO);

    // 验证工号对应学生是否存在
    public boolean checkStudentIsExistByNumber(String number);

    // 删除学生
    public void deleteStudent(Long id);

    void addStudent(AddStudentDTO param);

    // 获取学院学生列表
    PageResult getMajorStudentList(Long id, String keyWord, PageParamDTO param);
}

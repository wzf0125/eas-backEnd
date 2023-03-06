package org.quanta.eas.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.quanta.eas.dto.AddStudentDTO;
import org.quanta.eas.dto.EditStudentDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.*;
import org.quanta.eas.exception.BusinessException;
import org.quanta.eas.mapper.MajorMapper;
import org.quanta.eas.mapper.StudentMajorMapper;
import org.quanta.eas.mapper.StudentMapper;
import org.quanta.eas.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quanta.eas.utils.MD5Utils;
import org.quanta.eas.utils.PageResult;
import org.quanta.eas.vo.StudentInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wzf mqh hzq zjx
 * @since 2022-12-10
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    StudentMajorMapper studentMajorMapper;
    @Autowired
    MajorMapper majorMapper;

    @Override
    public PageResult getStudentList(String keyWord, PageParamDTO param) {
        List<StudentInfoVO> data = studentMapper.getStudentList(keyWord, param.getStart(), param.getPageSize());
        Long total = studentMapper.getStudentCount(keyWord);
        return new PageResult(data, total, param.getPageSize());
    }

    @Override
    @Transactional
    public void editStudent(EditStudentDTO param) {
        // 查询学生是否存在
        Student student = getById(param.getId());
        if (student == null) {
            throw new BusinessException("学生不存在");
        }

        // 如果要更新学号
        if (!param.getNumber().equals(student.getNumber())) {
            // 验证模板工号是否存在
            if (checkStudentIsExistByNumber(param.getNumber())) {
                throw new BusinessException("学号已存在");
            }
            student.setName(param.getNumber());
        }

        // 如果要更新姓名
        if (param.getName() != null) {
            student.setName(param.getName());
        }

        // 更新密码
        if (param.getPassword() != null) {
            String salt = MD5Utils.getSalt();
            student.setSalt(salt);
            student.setPassword(MD5Utils.md5(param.getPassword(), salt));
        }

        // 更新学生信息
        updateById(student);

        // 如果要更新学院信息
        if (param.getMajor() != null) {
            String majorName = param.getMajor();
            // 验证学院是否存在
            Major major = majorMapper.selectOne(new LambdaQueryWrapper<Major>().eq(Major::getMajor, majorName));
            if (major == null) {
                throw new BusinessException("学院不存在");
            }
            // 更新学院信息
            studentMajorMapper.update(null,
                    new LambdaUpdateWrapper<StudentMajor>()
                            .eq(StudentMajor::getStudentId, student.getId())
                            .set(StudentMajor::getMajorId, major.getId())
            );
        }
        // 如果要更新年级信息
        if (param.getYear() != null) {
            studentMajorMapper.update(null,
                    new LambdaUpdateWrapper<StudentMajor>()
                            .eq(StudentMajor::getStudentId, student.getId())
                            .set(StudentMajor::getYear, param.getYear()));
        }

    }

    /**
     * 判断学生是否存在
     * 存在返回true
     * 不存在返回false
     */
    @Override
    public boolean checkStudentIsExistByNumber(String number) {
        Student one = getOne(new LambdaUpdateWrapper<Student>().eq(Student::getNumber, number));
        return one != null;
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        boolean b = removeById(id);
        if (!b) {
            throw new BusinessException("删除学生失败");
        }
        studentMajorMapper.delete(
                new LambdaUpdateWrapper<StudentMajor>()
                        .eq(StudentMajor::getStudentId, id)
        );
    }

    @Override
    public void addStudent(AddStudentDTO param) {
        Long majorId = param.getMajor();
        // 验证学院是否存在
        Major major = majorMapper.selectById(majorId);
        if (major == null) {
            throw new BusinessException("学院不存在");
        }

        // 判断学号是否重复
        if (checkStudentIsExistByNumber(param.getNumber())) {
            throw new BusinessException("学号已经存在");
        }

        // 要插入的学生信息
        Student student = new Student();
        BeanUtils.copyProperties(param, student);
        String salt = MD5Utils.getSalt();
        student.setSalt(salt);
        // 加密密码
        student.setPassword(MD5Utils.md5(student.getPassword(), salt));
        save(student);

        // 插入学院信息
        studentMajorMapper.insert(new StudentMajor(student.getId(), major.getId(), param.getYear()));
    }

    @Override
    public PageResult getMajorStudentList(Long id, String keyWord, PageParamDTO param) {
        List<StudentInfoVO> majorStudentList = studentMapper.getMajorStudentList(id, keyWord, param.getStart(), param.getPageSize());
        Long total = studentMapper.getMajorStudentCount(id, keyWord);
        return new PageResult(majorStudentList, total, param.getPageSize());
    }


}

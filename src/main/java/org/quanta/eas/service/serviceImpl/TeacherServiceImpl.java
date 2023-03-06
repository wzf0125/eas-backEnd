package org.quanta.eas.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.quanta.eas.dto.AddTeacherDTO;
import org.quanta.eas.dto.EditTeacherDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.Major;
import org.quanta.eas.entity.Teacher;
import org.quanta.eas.entity.TeacherMajor;
import org.quanta.eas.exception.BusinessException;
import org.quanta.eas.mapper.MajorMapper;
import org.quanta.eas.mapper.TeacherMajorMapper;
import org.quanta.eas.mapper.TeacherMapper;
import org.quanta.eas.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quanta.eas.utils.MD5Utils;
import org.quanta.eas.utils.PageResult;
import org.quanta.eas.vo.TeacherInfoVO;
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
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    TeacherMajorMapper teacherMajorMapper;
    @Autowired
    MajorMapper majorMapper;

    @Override
    public PageResult getTeacherList(PageParamDTO page, String keyWord) {
        List<TeacherInfoVO> data = teacherMapper.getTeacherList(keyWord,page.getStart(), page.getPageSize());
        Long total = teacherMapper.getTeacherCount(keyWord);
        return new PageResult(data, total, page.getPageSize());
    }

    @Override
    @Transactional
    public void editTeacher(EditTeacherDTO data) {
        // 查询教师是否存在
        Teacher teacher = getById(data.getId());
        if (teacher == null) {
            throw new BusinessException("教师不存在");
        }
        // 如果要更新工号
        if (!data.getNumber().equals(teacher.getNumber())) {
            // 验证目标工号教师是否存在
            if (checkTeacherIsExistByNumber(data.getNumber())) {
                throw new BusinessException("工号已存在");
            }
            teacher.setNumber(data.getNumber());
        }
        // 如果要更新姓名
        if (data.getName() != null) {
            teacher.setName(data.getName());
        }

        // 更新密码
        if(data.getPassword()!=null){
            String salt = MD5Utils.getSalt();
            teacher.setPassword(MD5Utils.md5(data.getPassword(),salt));
            teacher.setSalt(salt);
        }

        // 更新教师信息
        updateById(teacher);

        // 更新学院信息
        // 需要编辑学院的情况
        if (data.getMajor() != null) {
            String majorName = data.getMajor();
            // 验证学院是否存在
            Major major = majorMapper.selectOne(new LambdaQueryWrapper<Major>().eq(Major::getMajor, majorName));
            if (major == null) {
                throw new BusinessException("学院不存在");
            }
            // 更新学院信息
            teacherMajorMapper.update(null,
                    new LambdaUpdateWrapper<TeacherMajor>()
                            .eq(TeacherMajor::getTeacherId, teacher.getId())
                            .set(TeacherMajor::getMajorId, major.getId())
            );
        }
    }

    @Override
    @Transactional
    public void deleteTeacher(Long id) {
        boolean b = removeById(id);
        if (!b) {
            throw new BusinessException("教师不存在");
        }
        teacherMajorMapper.delete(
                new LambdaUpdateWrapper<TeacherMajor>()
                        .eq(TeacherMajor::getTeacherId, id)
        );
    }

    @Override
    @Transactional
    public void addTeacher(AddTeacherDTO data) {
        Long majorId = data.getMajor();
        // 验证学院是否存在
        Major major = majorMapper.selectById(majorId);
        if (major == null) {
            throw new BusinessException("学院不存在");
        }

        // 验证教师是否存在
        if (checkTeacherIsExistByNumber(data.getNumber())) {
            throw new BusinessException("教师已存在");
        }

        // 插入教师信息
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(data, teacher);
        String salt = MD5Utils.getSalt();
        // 生成随机盐值
        teacher.setSalt(salt);
        // 加密密码
        teacher.setPassword(MD5Utils.md5(teacher.getPassword(), salt));
        // 插入教师信息
        save(teacher);

        // 插入学院信息
        teacherMajorMapper.insert( new TeacherMajor(teacher.getId(), major.getId()));
    }

    /**
     * 判断教师是否存在
     * 存在返回true
     * 不存在返回false
     */
    @Override
    public boolean checkTeacherIsExistByNumber(String number) {
        Teacher one = getOne(new LambdaUpdateWrapper<Teacher>().eq(Teacher::getNumber, number));
        return one != null;
    }

    /**
     * 获取学院教师列表
     */
    @Override
    public PageResult getMajorTeacherList(Long id, String keyWord, PageParamDTO param) {
        List<TeacherInfoVO> data = teacherMapper.getMajorTeacherList(id, keyWord, param.getStart(), param.getPageSize());
        Long total = teacherMapper.getMajorTeacherCount(id, keyWord);
        return new PageResult(data, total, param.getPageSize());
    }
}

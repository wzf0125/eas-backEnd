package org.quanta.eas.service.serviceImpl;

import org.quanta.eas.dto.EditScoreDTO;
import org.quanta.eas.dto.EditStudentDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.ClassStudent;
import org.quanta.eas.mapper.ClassStudentMapper;
import org.quanta.eas.pojo.StudentClassScore;
import org.quanta.eas.service.ClassStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.quanta.eas.utils.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ClassStudentServiceImpl extends ServiceImpl<ClassStudentMapper, ClassStudent> implements ClassStudentService {

    @Autowired
    ClassStudentMapper classStudentMapper;

    @Override
    public PageResult getStudentScore(Long sid,String term, PageParamDTO param) {
        List<StudentClassScore> studentScore = classStudentMapper.getStudentScore(sid, term,param.getStart(), param.getPageSize());
        Long total = classStudentMapper.getStudentScoreCount(sid,term);
        return new PageResult(studentScore, total, param.getPageSize());
    }

    @Override
    public void editStudentScore(EditScoreDTO param) {
        ClassStudent classStudent = new ClassStudent();
        BeanUtils.copyProperties(param, classStudent);
        classStudentMapper.updateById(classStudent);
    }
}

package org.quanta.eas.mapper;

import org.quanta.eas.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.eas.vo.StudentInfoVO;

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
public interface StudentMapper extends BaseMapper<Student> {
    public List<StudentInfoVO> getStudentList(String keyWord, int start, int size);

    public Long getStudentCount(String keyWord);

    public List<StudentInfoVO> getMajorStudentList(Long id, String keyWord, int start, int size);

    public Long getMajorStudentCount(Long id, String keyWord);
}

package org.quanta.eas.mapper;

import org.quanta.eas.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.eas.vo.TeacherInfoVO;

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
public interface TeacherMapper extends BaseMapper<Teacher> {
    // 获取教师信息列表
    public List<TeacherInfoVO> getTeacherList(String keyWord, int start, int size);

    // 获取教师总数
    public Long getTeacherCount(String keyWord);

    // 获取教师信息列表
    public List<TeacherInfoVO> getMajorTeacherList(Long id, String keyWord, int start, int size);

    // 获取教师总数
    public Long getMajorTeacherCount(Long id, String keyWord);

}

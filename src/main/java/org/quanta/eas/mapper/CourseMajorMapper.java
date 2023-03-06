package org.quanta.eas.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.eas.entity.CourseMajor;
import org.quanta.eas.pojo.CourseMajorInfo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wzf mqh hzq zjx
 * @since 2022-12-10
 */
@Mapper
public interface CourseMajorMapper extends BaseMapper<CourseMajor> {
    public List<CourseMajorInfo> getCourseMajor(Long id);
}

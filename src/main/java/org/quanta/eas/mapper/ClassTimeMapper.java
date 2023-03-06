package org.quanta.eas.mapper;

import org.quanta.eas.dto.ClassTimeDO;
import org.quanta.eas.entity.ClassTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.eas.pojo.ClassTimeInfo;

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
public interface ClassTimeMapper extends BaseMapper<ClassTime> {
    // 获取课程时间
    ClassTimeInfo getClassTime(Long id);
    // 获取课程时间列表
    List<ClassTimeInfo> getClassTimeList(List<Long> id);
}

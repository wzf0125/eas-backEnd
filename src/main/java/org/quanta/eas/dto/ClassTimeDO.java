package org.quanta.eas.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/16
 */
@Data
public class ClassTimeDO {
    /**
     * 课程星期
     */
    @NotNull(message = "课程星期不能为空")
    @Min(value = 1, message = "课程星期数范围为1-7")
    @Max(value = 7, message = "课程星期数范围为1-7")
    private Integer week;

    /**
     * 课程节数
     */
    @NotNull(message = "课程节数不能为空")
    @Min(value = 1, message = "课程节数范围为1-15节")
    @Max(value = 15, message = "课程节数范围为1-15节")
    private List<Integer> lesson;

}

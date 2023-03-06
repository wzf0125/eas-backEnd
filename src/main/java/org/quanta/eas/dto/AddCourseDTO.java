package org.quanta.eas.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/12
 */
@Data
public class AddCourseDTO {
    /**
     * 课程名称
     */
    @NotNull(message = "课程名称不能为空")
    @Length(min = 2, max = 50, message = "课程名称格式错误")
    private String name;

    /**
     * 课程号
     */
    @NotNull(message = "课程号不能为空")
    @Length(min = 2, max = 50, message = "课程名称格式错误")
    private String number;

    @NotNull
    @Min(0)
    private Long majorId;

    /**
     * 可选课学院列表
     */
    @NotNull(message = "可选可学院列表不能为空")
    private List<@Valid CourseYearDO> allow;
}

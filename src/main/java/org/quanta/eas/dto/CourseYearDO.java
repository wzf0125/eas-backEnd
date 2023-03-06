package org.quanta.eas.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
public class CourseYearDO {
    @NotNull(message = "学院id不能为空")
    @Min(value = 0, message = "学院id格式错误")
    Long majorId;

    @NotNull(message = "课程学年不能为空")
    List<@NotNull @Length(min = 4, max = 4, message = "年级格式错误") String> year;
}

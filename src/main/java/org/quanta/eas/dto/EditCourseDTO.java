package org.quanta.eas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditCourseDTO {
    @Min(value = 0,message = "id格式错误")
    Long id;

    @Length(min = 2,max = 20,message = "课程号格式错误")
    String number;

    @Length(min = 2,max = 50,message = "课程名称格式错误")
    String name;

    String major;
}

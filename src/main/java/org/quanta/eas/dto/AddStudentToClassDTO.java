package org.quanta.eas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentToClassDTO {
    @NotNull
    @Length(min = 9, max = 12, message = "学号格式错误")
    private String number;
    @NotNull
    @Min(value = 0, message = "课程id错误")
    private Long classId;
}

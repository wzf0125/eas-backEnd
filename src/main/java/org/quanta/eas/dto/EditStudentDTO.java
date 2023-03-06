package org.quanta.eas.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/12
 */
@Data
public class EditStudentDTO {
    /**
     * pk_id
     */
    @Min(0)
    @NotNull
    Long id;

    /**
     * 学生姓名
     */
    @Length(min = 1, max = 30, message = "姓名格式错误")
    String name;

    /**
     * 工号
     */
    @Length(min = 11, max = 11, message = "学号格式错误")
    String number;

    /**
     * 学院
     */
    String major;

    /**
     * 年级
     */
    @Length(min = 4, max = 4, message = "年级格式错误")
    String year;

    /**
     * 修改密码
     * */
    @Length(min = 6, max = 50, message = "密码格式错误")
    String password;
}

package org.quanta.eas.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/12
 */
@Data
public class AddStudentDTO {

    /**
     * 教师姓名
     */
    @NotNull(message = "姓名不能为空")
    @Length(min = 1, max = 30, message = "姓名格式错误")
    private String name;

    /**
     * 工号
     */
    @NotNull(message = "工号不能为空")
    @Length(min = 11,max = 11,message = "工号格式错误")
    private String number;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    @Length(min = 6,max = 50,message = "格式错误")
    private String password;

    /**
     * 教师学院
     */
    @NotNull(message = "学院不能为空")
    private Long major;

    /**
     * 年级
     */
    @Length(min = 4, max = 4, message = "年级格式错误")
    String year;
}

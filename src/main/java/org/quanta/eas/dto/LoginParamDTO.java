package org.quanta.eas.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/11
 */
@Data
public class LoginParamDTO {
    @NotNull
    @Length(min = 11, max = 11, message = "账号格式错误")
    String number;

    @NotNull
    @Length(min = 6, max = 50, message = "密码格式错误")
    String password;

    @NotNull(message = "登录类型不能为空")
    @Min(value = 0, message = "登录类型错误")
    @Max(value = 2, message = "登录类型错误")
    Integer type;
}

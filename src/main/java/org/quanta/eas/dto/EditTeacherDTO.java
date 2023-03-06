package org.quanta.eas.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/12
 */
@Data
public class EditTeacherDTO {
    /**
     * pk_id
     */
    Long id;

    /**
     * 教师姓名
     */
    @Length(min = 1, max = 30, message = "姓名格式错误")
    String name;

    /**
     * 工号
     */
    @Length(min = 9, max = 12, message = "学号格式错误")
    String number;

    /**
     * 学院
     */
    String major;

    /**
     * 修改密码
     * */
    @Length(min = 6, max = 50, message = "密码格式错误")
    String password;
}

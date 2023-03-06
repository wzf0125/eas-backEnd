package org.quanta.eas.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherInfoVO {
    /**
     * pk_id
     */
    private Long id;

    /**
     * 教师姓名
     */
    private String name;

    /**
     * 工号
     */
    private String number;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 学院名称
     */
    private String major;
}

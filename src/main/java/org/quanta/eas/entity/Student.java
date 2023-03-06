package org.quanta.eas.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author wzf mqh hzq zjx
 * @since 2022-12-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * pk_id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学生姓名
     */
    @TableField("name")
    private String name;

    /**
     * 学号
     */
    @TableField("number")
    private String number;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 密码盐值
     */
    @TableField("salt")
    private String salt;

    /**
     * 逻辑删除(now()为已删除 默认为null)
     */
    @TableLogic(value = "null",delval = "now()")
    private Date isDeleted;

}

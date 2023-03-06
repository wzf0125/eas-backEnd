package org.quanta.eas.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * pk_id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程名称
     */
    @TableField("name")
    private String name;

    /**
     * 课程号
     */
    @TableField("number")
    private String number;

    /**
     * 开课单位id
     * */
    @TableField("major_id")
    private Long majorId;

    /**
     * 逻辑删除(1为已删除 默认为0)
     */
    @TableLogic
    private Integer isDeleted;

}

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
@TableName("class")
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * pk_id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 班级名称
     */
    @TableField("name")
    private String name;

    /**
     * 班级人数
     */
    @TableField("capacity")
    private Integer capacity;

    /**
     * 学期
     */
    @TableField("term")
    private String term;

    /**
     * 教师id
     */
    @TableField("teacher_id")
    private Long teacherId;

    /**
     * 平时成绩百分比
     */
    @TableField("usual_percentage")
    private Double usualPercentage;

    /**
     * 最终成绩百分比
     */
    @TableField("final_percentage")
    private Double finalPercentage;

    /**
     * 是否结课 0 为未结课 1为已结课
     */
    @TableField("is_closed")
    private Integer isClosed;

    /**
     * 逻辑删除(1为已删除 默认为0)
     */
    @TableLogic
    private Integer isDeleted;


}

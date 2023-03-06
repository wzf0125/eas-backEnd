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
@TableName("class_time")
public class ClassTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("class_id")
    private Long classId;

    /**
     * 课程星期
     */
    @TableField("week")
    private Integer week;

    /**
     * 课程节数
     */
    @TableField("lesson")
    private Integer lesson;

    /**
     * 逻辑删除(1为已删除 默认为0)
     */
    @TableLogic
    private Integer isDeleted;

    public ClassTime(Long classId, Integer week, Integer lesson) {
        this.classId = classId;
        this.week = week;
        this.lesson = lesson;
    }
}

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
@TableName("course_major")
public class CourseMajor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * pk_id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程id
     */
    @TableField("course_id")
    private Long courseId;

    /**
     * 可选可学院
     */
    @TableField("major_id")
    private Long majorId;

    /**
     * 逻辑删除(1为已删除 默认为0)
     */
    @TableLogic
    private Integer isDeleted;

    public CourseMajor(Long courseId, Long majorId) {
        this.courseId = courseId;
        this.majorId = majorId;
    }
}

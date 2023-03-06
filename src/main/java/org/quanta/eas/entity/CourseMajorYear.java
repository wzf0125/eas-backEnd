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
 * @since 2022-12-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("course_major_year")
public class CourseMajorYear implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * pk_id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程可选课学院id
     */
    @TableField("course_major_id")
    private Long courseMajorId;

    /**
     * 学年
     */
    @TableField("year")
    private String year;

    /**
     * 逻辑删除(1为已删除 默认为0)
     */
    @TableLogic
    private Integer isDeleted;

    public CourseMajorYear(Long courseMajorId, String year) {
        this.courseMajorId = courseMajorId;
        this.year = year;
    }
}

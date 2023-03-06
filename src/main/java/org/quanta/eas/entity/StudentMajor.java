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
@TableName("student_major")
public class StudentMajor implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * pk_id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 学生id
     */
    @TableField("student_id")
    private Long studentId;

    /**
     * 学院id
     */
    @TableField("major_id")
    private Long majorId;

    /**
     * 年级
     */
    @TableField("year")
    private String year;

    /**
     * 逻辑删除(1为已删除 默认为0)
     */
    @TableLogic
    private Integer isDeleted;

    public StudentMajor(Long studentId, Long majorId, String year) {
        this.studentId = studentId;
        this.majorId = majorId;
        this.year = year;
    }
}

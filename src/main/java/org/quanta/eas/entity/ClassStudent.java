package org.quanta.eas.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("class_student")
public class ClassStudent implements Serializable {

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
     * 课程id
     */
    @TableField("class_id")
    private Long classId;

    /**
     * 平时成绩
     */
    @TableField("usual_score")
    private Double usualScore;

    /**
     * 期末成绩
     */
    @TableField("final_score")
    private Double finalScore;

    /**
     * 逻辑删除(1为已删除 默认为0)
     */
    @TableLogic
    private Integer isDeleted;

    public ClassStudent(Long studentId, Long classId) {
        this.studentId = studentId;
        this.classId = classId;
        this.usualScore = 0d;
        this.finalScore = 0d;
    }
}

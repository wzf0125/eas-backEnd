package org.quanta.eas.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassStudentInfo {
    /**
     * pk_id
     */
    private Long id;

    /**
     * 学生id
     */
    private Long studentId;

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学生学号
     */
    private String number;

    /**
     * 学生学院
     */
    private String major;

    /**
     * 平时成绩
     */
    private Double usualScore;

    /**
     * 期末成绩
     */
    private Double finalScore;

    /**
     * 总成绩
     */
    private Double score;
}

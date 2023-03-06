package org.quanta.eas.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentClassScore {
    // id
    private Long id;
    // 学期
    private String term;
    // 课程名称
    private String courseName;
    // 班级名称
    private String className;
    // 平时成绩
    private Double usualScore;
    // 最终成绩
    private Double finalScore;
    // 总成绩
    private Double score;

    // 课程状态
    private Integer isClosed;
}

package org.quanta.eas.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.quanta.eas.dto.ClassTimeDO;
import org.quanta.eas.pojo.ClassStudentInfo;
import org.quanta.eas.pojo.ClassTimeInfo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

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
public class ClassInfoVO {
    /**
     * 班级id
     */
    private Long id;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 课程名称
     * */
    private String courseName;

    /**
     * 课程号
     * */
    private String courseNumber;

    /**
     * 剩余容量
     * */
    private Integer surplus;

    /**
     * 班级人数
     */
    private Integer capacity;

    /**
     * 学期
     */
    private String term;

    /**
     * 教师id
     */
    private Integer teacherId;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 平时成绩百分比
     */
    private Double usualPercentage;

    /**
     * 最终成绩百分比
     */
    private Double finalPercentage;

    /**
     * 上课时间
     */
    ClassTimeInfo classTime;

    /**
     * 学生列表
     */
    private List<ClassStudentInfo> studentList;
}

package org.quanta.eas.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Max;
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
public class AddClassDTO {
    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 班级名称
     */
    @NotNull(message = "班级名称不能为空")
    private String name;

    /**
     * 班级人数
     */
    @NotNull(message = "班级人数不能为空")
    @Min(value = 0, message = "班级人数不能小于0")
    private Integer capacity;

    /**
     * 学期
     */
    @NotNull(message = "学期不能为空")
    @Length(min = 9, max = 9, message = "学期格式错误")
    private String term;

    /**
     * 教师id
     */
    @NotNull(message = "教师工号不能为空")
    @Length(min = 0, max = 20 ,message = "教师工号格式错误")
    private String teacherNumber;

    @NotNull(message = "课程时间不能为空")
    ClassTimeDO classTime;

    /**
     * 平时成绩百分比
     */
    @Min(value = 0, message = "平时成绩格式错误")
    @Max(value = 1,message = "平时成绩格式错误")
    private Double usualPercentage;

    /**
     * 最终成绩百分比
     */
    @Min(value = 0, message = "期末成绩格式错误")
    @Max(value = 1,message = "期末成绩格式错误")
    private Double finalPercentage;
}

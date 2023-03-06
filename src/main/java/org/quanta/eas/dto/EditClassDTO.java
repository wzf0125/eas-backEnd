package org.quanta.eas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditClassDTO {
    @Min(0)
    private Long id;

    @Length(min = 2,max = 50,message = "教师名称错误")
    private String name;

    @Length(min = 9, max = 12, message = "教师工号错误")
    private String number;

    @Min(value = 0, message = "平时成绩百分比格式错误")
    @Max(value = 1, message = "平时成绩百分比格式错误")
    private Double usualPercentage;

    @Min(value = 0, message = "期末成绩百分比格式错误")
    @Max(value = 1, message = "期末成绩百分比格式错误")
    private Double finalPercentage;

    @Length(min = 9, max = 9, message = "学期格式错误")
    private String term;

    @Min(value = 0, message = "班级容量格式错误")
    private Integer capacity;

    @Min(value = 0,message = "班级状态错误")
    @Max(value = 1,message = "班级状态错误")
    private Integer isClosed;
}

package org.quanta.eas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class EditScoreDTO {
    @Min(value = 0, message = "id格式错误")
    Long id;
    @Min(value = 0, message = "平时成绩格式错误")
    @Max(value = 100, message = "平时成绩格式错误")
    Double usualScore;

    @Min(value = 0, message = "期末成绩格式错误")
    @Max(value = 100, message = "期末成绩格式错误")
    Double finalScore;
}

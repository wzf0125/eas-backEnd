package org.quanta.eas.pojo;

import lombok.Data;

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
public class ClassTimeInfo {
    /**
     * 班级id
     */
    private Long classId;

    /**
     * 班级上课星期
     */
    private Integer week;

    /**
     * 课程节数
     */
    private List<Integer> lesson;
}

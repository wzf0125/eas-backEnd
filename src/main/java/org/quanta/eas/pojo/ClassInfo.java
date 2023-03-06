package org.quanta.eas.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/15
 */
@Data
public class ClassInfo {
    /**
     * pk_id
     */
    private Long id;

    /**
     * 班级名称
     */
    private String name;

    /**
     * 剩余容量
     * */
    private Integer surplus;

    /**
     * 班级人数
     */
    private Integer capacity;

    /**
     * 教师id
     */
    private Integer teacherId;

    /**
     * 授课教师姓名
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
     * 是否结课 0 为未结课 1为已结课
     */
    private Integer isClosed;

    /**
     * 学期
     * */
    private String term;

    private ClassTimeInfo classTime;
}

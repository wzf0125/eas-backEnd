package org.quanta.eas.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.quanta.eas.entity.CourseMajor;

import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/15
 */
@Data
public class CourseInfo {
    /**
     * pk_id
     */
    private Long id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程号
     */
    private String number;

    /**
     * 开课学院
     */
    private String major;
}

package org.quanta.eas.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quanta.eas.entity.Course;

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
public class CourseClassInfo {
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

    /**
     * 班级信息列表
     */
    List<ClassInfo> classList;
}

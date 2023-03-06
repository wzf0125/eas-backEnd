package org.quanta.eas.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.quanta.eas.pojo.CourseClassInfo;

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
public class TeacherCourseClassVO {
    List<CourseClassInfo> courseClassList;
}

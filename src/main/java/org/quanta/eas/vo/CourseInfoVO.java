package org.quanta.eas.vo;

import lombok.Data;
import org.quanta.eas.pojo.ClassInfo;
import org.quanta.eas.pojo.CourseInfo;
import org.quanta.eas.pojo.CourseMajorInfo;

import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/15
 */
@Data
public class CourseInfoVO {
    CourseInfo courseInfo;
    List<CourseMajorInfo> allowMajor;
    List<ClassInfo> classInfoList;

    public CourseInfoVO() {
    }

    public CourseInfoVO(CourseInfo courseInfo, List<CourseMajorInfo> allowMajor, List<ClassInfo> classInfoList) {
        this.courseInfo = courseInfo;
        this.allowMajor = allowMajor;
        this.classInfoList = classInfoList;
    }
}

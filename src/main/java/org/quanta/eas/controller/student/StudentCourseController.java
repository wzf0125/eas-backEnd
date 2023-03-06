package org.quanta.eas.controller.student;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.bean.Response;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/10
 */
@RestController
@RequestMapping("/student/course")
@Permission({RoleConstants.ROLE_STUDENT})
@Api(tags = "学生端-课程模块")
public class StudentCourseController extends BaseController {
    @Autowired
    CourseService courseService;

    /**
     * [S001]获取课程列表
     * GET /student/course
     * 接口ID：54137549
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54137549
     */
    @GetMapping
    @ApiOperation("[S001]获取课程列表")
    public Response<Object> getCourseList(@Validated PageParamDTO param, String keyWord,
                                          @Validated
                                          @NotNull(message = "学期不能为空")
                                          @Length(min = 9, max = 9, message = "学期格式错误") String term) {
        return Response.success(courseService.getStudentCourseList(getUid(),keyWord, term, param));
    }

    /**
     * [S002]选课
     * GET /student/course/class/{id}
     * 接口ID：54137571
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54137571
     */
    @PostMapping("/class/{id}")
    @ApiOperation("[S002]选课")
    public Response<Object> choiceCourse(@PathVariable Long id) {
        // 选课
        courseService.choiceCourse(id, getUid());
        return Response.success();
    }

    /**
     * [S003]退课
     * DELETE /student/course/class/{id}
     * 接口ID：54137639
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54137639
     */
    @DeleteMapping("/class/{id}")
    @ApiOperation("[S003]退课")
    public Response<Object> delClass(@PathVariable Long id) {
        // 取消选课
        courseService.cancelChoiceCourse(id, getUid());
        return Response.success();
    }

    /**
     * [S004]课表查看
     * GET /student/course
     * 接口ID：54137784
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54137784
     */
    @GetMapping("/class")
    @ApiOperation("[S004]课表查看")
    public Response<Object> getMyCourseList(@Validated @Length(min = 9, max = 9, message = "学期格式错误") String term) {
        return Response.success(courseService.getStudentClassTable(getUid(), term));
    }

    /**
     * [S007]获取已选课程
     * GET /student/course/class/choice
     * 接口ID：56001486
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-56001486
     */
    @GetMapping("/class/choice")
    @ApiOperation("[S007]获取已选课程")
    public Response<Object> getMyCourseClassList(@Validated @Length(min = 9, max = 9, message = "学期格式错误") String term,
                                                 String keyWord,
                                                 @Validated PageParamDTO param) {
        return Response.success(courseService.getStudentClassList(getUid(), term, keyWord, param));
    }
}

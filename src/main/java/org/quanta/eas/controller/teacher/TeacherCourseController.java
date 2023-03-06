package org.quanta.eas.controller.teacher;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.bean.Response;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.quanta.eas.dto.EditClassDTO;
import org.quanta.eas.dto.EditCourseDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.Class;
import org.quanta.eas.exception.PermissionException;
import org.quanta.eas.service.ClassService;
import org.quanta.eas.service.CourseService;
import org.quanta.eas.utils.Assert;
import org.quanta.eas.vo.CourseInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/10
 */
@RestController
@RequestMapping("/teacher/course")
@Permission({RoleConstants.ROLE_TEACHER})
@Api(tags = "教师端-课程模块")
public class TeacherCourseController extends BaseController {
    @Autowired
    ClassService classService;
    @Autowired
    CourseService courseService;

    /**
     * [T001]查看课程列表
     * GET /teacher/course
     * 接口ID：54138364
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54138364
     */
    @GetMapping
    @ApiOperation("[T001]查看课程列表")
    public Response<Object> getCourseList(@Validated @Length(min = 9, max = 9, message = "学期格式错误") String term,
                                          String keyWord,
                                          @Validated PageParamDTO param) {
        return Response.success(classService.getTeacherCourseClassList(getUid(), term, keyWord, param));
    }

//    /**
//     * [T002]编辑课程信息
//     * PUT /teacher/course
//     * 接口ID：54139684
//     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54139684
//     */
//    @PutMapping
//    public Response<Object> editCourse(@RequestBody @Validated EditClassDTO param) {
//        return Response.success();
//    }

    /**
     * [T003]查看课程班级列表
     * GET /teacher/course/{id}
     * 接口ID：54138370
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54138370
     */
    @GetMapping("/{id}")
    @ApiOperation("[T003]查看课程班级列表")
    public Response<Object> getCourseClass(@PathVariable Long id) {
        // 查询对应课程详细信息 包括课程详细和课程班级信息 可选课学院和年级信息
        return Response.success(courseService.getCourse(id, getUid()));
    }

    /**
     * [T007]教师获取课表
     * GET /teacher/course/table
     * 接口ID：56022575
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-56022575
     */
    @GetMapping("/table")
    @ApiOperation("[T007]教师获取课表")
    public Response<Object> getClassTable(@Validated @Length(min = 9, max = 9, message = "学期格式错误") String term) {
        return Response.success(courseService.getTeacherClassTable(getUid(), term));
    }
}

package org.quanta.eas.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.bean.Response;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.quanta.eas.dto.AddCourseDTO;
import org.quanta.eas.dto.EditCourseDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.service.CourseService;
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
@RequestMapping("/admin/course")
@Permission({RoleConstants.ROLE_ADMIN})
@Api(tags = "管理端-课程管理")
public class AdminCourseController extends BaseController {
    @Autowired
    CourseService courseService;

    /**
     * [A009]查看课程列表
     * GET /admin/course
     * 接口ID：54145406
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54145406
     */
    @GetMapping
    @ApiOperation("[A009]查看课程列表")
    public Response<Object> getCourseList(String keyWord, @Validated PageParamDTO param) {
        return Response.success(courseService.getCourseList(param, keyWord));
    }

    /**
     * [A010]添加课程
     * POST /admin/course
     * 接口ID：54145905
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54145905
     */
    @PostMapping
    @ApiOperation("[A010]添加课程")
    public Response<Object> addCourse(@RequestBody @Validated AddCourseDTO param) {
        courseService.addCourse(param);
        return Response.success();
    }

    /**
     * [A011]获取课程详情
     * GET /admin/course/{id}
     * 接口ID：54145961
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54145961
     */
    @GetMapping("/{id}")
    @ApiOperation("[A011]获取课程详情")
    public Response<Object> getCourseClassList(@PathVariable Long id) {
        return Response.success(courseService.getCourse(id));
    }

    /**
     * [A012]编辑课程信息
     * PUT /admin/course
     * 接口ID：54146314
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54146314
     */
    @PutMapping
    @ApiOperation("[A012]编辑课程信息")
    public Response<Object> editCourse(@RequestBody @Validated EditCourseDTO param) {
        courseService.editCourse(param);
        return Response.success();
    }

    /**
     * [A013]删除课程
     * DELETE /admin/course/{id}
     * 接口ID：54146360
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54146360
     */
    @DeleteMapping("/{id}")
    @ApiOperation("[A013]删除课程")
    public Response<Object> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return Response.success();
    }


}

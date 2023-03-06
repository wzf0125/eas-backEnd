package org.quanta.eas.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.bean.Response;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.quanta.eas.dto.AddClassDTO;
import org.quanta.eas.dto.AddStudentToClassDTO;
import org.quanta.eas.dto.EditClassDTO;
import org.quanta.eas.entity.ClassStudent;
import org.quanta.eas.service.ClassService;
import org.quanta.eas.service.ClassStudentService;
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
@RequestMapping("/admin/course/class")
@Permission({RoleConstants.ROLE_ADMIN})
@Api(tags = "管理端-班级管理")
public class AdminClassController extends BaseController {

    @Autowired
    ClassService classService;
    @Autowired
    ClassStudentService classStudentService;

    /**
     * [A014]添加课程班级
     * GET /admin/course/class
     * 接口ID：54166691
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54166691
     */
    @PostMapping
    @ApiOperation("[A014]添加课程班级")
    public Response<Object> addClass(@RequestBody @Validated AddClassDTO param) {
        classService.addClass(param);
        return Response.success();
    }

    /**
     * [A015]编辑班级信息
     * PUT /admin/course/class
     * 接口ID：54166711
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54166711
     */
    @PutMapping
    @ApiOperation("[A015]编辑班级信息")
    public Response<Object> editClass(@RequestBody @Validated EditClassDTO param) {
        classService.editClass(param);
        return Response.success();
    }

    /**
     * [A016]删除班级
     * DELETE /admin/course/class/{id}
     * 接口ID：54166730
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54166730
     */
    @DeleteMapping("/{id}")
    @ApiOperation("[A016]删除班级")
    public Response<Object> deleteClass(@PathVariable Long id) {
        classService.deleteClass(id);
        return Response.success();
    }

    /**
     * [A019]获取班级详情
     * GET /admin/course/class/{id}
     * 接口ID：55059676
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-55059676
     */
    @GetMapping("/{id}")
    @ApiOperation("[A019]获取班级详情")
    public Response<Object> getClassInfo(@PathVariable Long id) {
        return Response.success(classService.getClassInfo(id));
    }

    /**
     * [A024]向班级中添加学生
     * POST /admin/course/class/student
     * 接口ID：55964119
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-55964119
     */
    @PostMapping("/student")
    @ApiOperation("[A024]向班级中添加学生")
    public Response<Object> addStudent2Class(@RequestBody @Validated AddStudentToClassDTO param) {
        classService.addStudent2Class(param);
        return Response.success();
    }

    /**
     * [A025]从班级删除学生
     * DELETE /admin/course/class/student/{id}
     * 接口ID：55967202
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-55967202
     */
    @DeleteMapping("/student/{id}")
    @ApiOperation("[A025]从班级删除学生")
    public Response<Object> removeStudent(@PathVariable Long id) {
        classStudentService.removeById(id);
        return Response.success();
    }
}

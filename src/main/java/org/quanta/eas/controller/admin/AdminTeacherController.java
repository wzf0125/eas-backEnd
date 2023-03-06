package org.quanta.eas.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.bean.Response;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.quanta.eas.dto.AddTeacherDTO;
import org.quanta.eas.dto.EditTeacherDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.exception.ParamException;
import org.quanta.eas.service.ClassService;
import org.quanta.eas.service.TeacherService;
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
@RequestMapping("/admin/teacher")
@Permission({RoleConstants.ROLE_ADMIN})
@Api(tags = "管理端-教师管理")
public class AdminTeacherController extends BaseController {
    @Autowired
    TeacherService teacherService;
    @Autowired
    ClassService classService;

    /**
     * [A001]获取教师列表
     * GET /admin/teacher
     * 接口ID：54143290
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54143290
     */
    @GetMapping
    @ApiOperation("[A001]获取教师列表")
    public Response<Object> getTeacherList(String keyWord,@Validated PageParamDTO data) {
        return Response.success(teacherService.getTeacherList(data, keyWord));
    }

    /**
     * [A002]编辑教师
     * PUT /admin/teacher
     * 接口ID：54143311
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54143311
     */
    @PutMapping
    @ApiOperation("[A002]编辑教师")
    public Response<Object> editTeacher(@RequestBody @Validated EditTeacherDTO data) {
        teacherService.editTeacher(data);
        return Response.success();
    }

    /**
     * [A003]删除教师
     * DELETE /admin/teacher/{id}
     * 接口ID：54143346
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54143346
     */
    @DeleteMapping("/{id}")
    @ApiOperation("[A003]删除教师")
    public Response<Object> deleteTeacher(@PathVariable Long id) {
        if (id == null || id < 0) {
            throw new ParamException();
        }
        teacherService.deleteTeacher(id);
        return Response.success();
    }

    /**
     * [A004]获取教师课程列表
     * GET /admin/teacher/{id}
     * 接口ID：54143391
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54143391
     */
    @GetMapping("/{id}")
    @ApiOperation("[A004]获取教师课程列表")
    public Response<Object> getTeacherInfo(@PathVariable Long id, @Validated PageParamDTO param) {
        return Response.success(classService.getTeacherCourseClassList(id, null, null, param));
    }

    /**
     * [A017]添加教师
     * POST /admin/teacher
     * 接口ID：54207628
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54207628
     */
    @PostMapping
    @ApiOperation("[A017]添加教师")
    public Response<Object> addTeacher(@Validated @RequestBody AddTeacherDTO data) {
        teacherService.addTeacher(data);
        return Response.success();
    }
}

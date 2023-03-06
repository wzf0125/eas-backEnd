package org.quanta.eas.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.bean.Response;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.quanta.eas.dto.AddStudentDTO;
import org.quanta.eas.dto.EditScoreDTO;
import org.quanta.eas.dto.EditStudentDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.exception.ParamException;
import org.quanta.eas.service.ClassStudentService;
import org.quanta.eas.service.StudentService;
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
@RequestMapping("/admin/student")
@Permission({RoleConstants.ROLE_ADMIN})
@Api(tags = "管理端-学生管理")
public class AdminStudentController extends BaseController {
    @Autowired
    StudentService studentService;
    @Autowired
    ClassStudentService classStudentService;

    /**
     * [A005]获取学生列表
     * GET /admin/student
     * 接口ID：54143733
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54143733
     */
    @GetMapping
    @ApiOperation("[A005]获取学生列表")
    public Response<Object> getStudentList(String keyWord, @Validated PageParamDTO data) {
        return Response.success(studentService.getStudentList(keyWord, data));
    }

    /**
     * [A006]编辑学生信息
     * PUT /admin/student
     * 接口ID：54143901
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54143901
     */
    @PutMapping
    @ApiOperation("[A006]编辑学生信息")
    public Response<Object> editStudent(@RequestBody @Validated EditStudentDTO param) {
        studentService.editStudent(param);
        return Response.success();
    }

    /**
     * [A007]删除学生
     * DELETE /admin/student/{id}
     * 接口ID：54144068
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54144068
     */
    @DeleteMapping("/{id}")
    @ApiOperation("[A007]删除学生")
    public Response<Object> deleteStudent(@PathVariable Long id) {
        if (id == null || id < 0) {
            throw new ParamException();
        }
        studentService.deleteStudent(id);
        return Response.success();
    }

    /**
     * [A008]获取学生成绩信息
     * GET /admin/student/{id}
     * 接口ID：54144276
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54144276
     */
    @GetMapping("/{id}")
    @ApiOperation("[A008]获取学生成绩信息")
    public Response<Object> getStudentInfo(@PathVariable Long id, @Validated PageParamDTO param) {
        return Response.success(classStudentService.getStudentScore(id, null, param));
    }

    /**
     * [A018]添加学生
     * POST /admin/student
     * 接口ID：54207684
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54207684
     */
    @PostMapping
    @ApiOperation("[A018]添加学生")
    public Response<Object> addStudent(@RequestBody @Validated AddStudentDTO param) {
        studentService.addStudent(param);
        return Response.success();
    }

    /**
     * [A023]编辑学生成绩
     * PUT /admin/student/score
     * 接口ID：55964094
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-55964094
     */
    @PutMapping("/score")
    @ApiOperation("[A023]编辑学生成绩")
    public Response<Object> editScore(@RequestBody @Validated EditScoreDTO param) {
        classStudentService.editStudentScore(param);
        return Response.success();
    }
}

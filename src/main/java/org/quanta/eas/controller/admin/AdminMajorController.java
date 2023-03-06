package org.quanta.eas.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.bean.Response;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.service.MajorService;
import org.quanta.eas.service.StudentService;
import org.quanta.eas.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/22
 */
@RestController
@RequestMapping("/admin/major")
@Permission({RoleConstants.ROLE_ADMIN})
@Api(tags = "管理端-学院管理")
public class AdminMajorController extends BaseController {
    @Autowired
    private MajorService majorService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    /**
     * [A020]获取学院列表
     * GET /admin/major
     * 接口ID：55301360
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-55301360
     */
    @GetMapping
    @ApiOperation("[A020]获取学院列表")
    public Response<Object> getMajorList() {
        return Response.success(majorService.list());
    }

    /**
     * [A021]获取学院学生信息
     * GET /admin/major/student/{id}
     * 接口ID：55301362
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-55301362
     */
    @GetMapping("/student/{id}")
    @ApiOperation("[A021]获取学院学生信息")
    public Response<Object> getMajorStudentList(@PathVariable Long id,
                                                String keyWord,
                                                @Validated PageParamDTO param) {
        return Response.success(studentService.getMajorStudentList(id,keyWord,param));
    }

    /**
     * [A022]获取学院老师信息
     * GET /admin/major/teacher/{id}
     * 接口ID：55725189
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-55725189
     */
    @GetMapping("/teacher/{id}")
    @ApiOperation("[A022]获取学院老师信息")
    public Response<Object> getMajorTeacherList(@PathVariable Long id,
                                                String keyWord,
                                                @Validated PageParamDTO param) {
        return Response.success(teacherService.getMajorTeacherList(id,keyWord,param));
    }

}

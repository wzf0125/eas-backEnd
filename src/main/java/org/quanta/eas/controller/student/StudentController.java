package org.quanta.eas.controller.student;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.bean.Response;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/10
 */
@RestController
@RequestMapping("/student")
@Permission({RoleConstants.ROLE_STUDENT})
@Api(tags = "学生端-学生模块")
public class StudentController extends BaseController {
    /**
     * [S006]查看学生个人信息
     * GET /student/info
     * 接口ID：54138114
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54138114
     */
    @GetMapping("/info")
    @ApiOperation("[S006]查看学生个人信息")
    public Response<Object> getUserInfo() {
        return Response.success();
    }
}

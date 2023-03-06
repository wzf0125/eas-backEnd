package org.quanta.eas.controller.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.bean.Response;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.quanta.eas.dto.LoginParamDTO;
import org.quanta.eas.entity.Admin;
import org.quanta.eas.entity.Student;
import org.quanta.eas.entity.Teacher;
import org.quanta.eas.exception.BusinessException;
import org.quanta.eas.service.AdminService;
import org.quanta.eas.service.StudentService;
import org.quanta.eas.service.TeacherService;
import org.quanta.eas.utils.Assert;
import org.quanta.eas.utils.AssertParam;
import org.quanta.eas.utils.MD5Utils;
import org.quanta.eas.utils.TokenUtils;
import org.quanta.eas.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/10
 */
@RestController
@RequestMapping("/common")
@Api(tags = "通用")
public class CommonController extends BaseController {
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    AdminService adminService;
    @Autowired
    TokenUtils tokenUtils;

    /**
     * [C001]登陆
     * GET /common/login
     * 接口ID：54141066
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54141066
     */
    @PostMapping("/login")
    @ApiOperation("[C001]登陆")
    public Response<Object> login(@RequestBody @Validated LoginParamDTO data) {
        Long id;
        int role;
        UserInfoVO userInfoVO = new UserInfoVO();
        switch (data.getType()) {
            // 学生登录
            case RoleConstants.ROLE_STUDENT:
                Student student = studentService.getOne(new LambdaQueryWrapper<Student>()
                        .eq(Student::getNumber, data.getNumber()));
                Assert.isNull(student, "账号不存在");
                Assert.isFalse(MD5Utils.compare(data.getPassword(), student.getSalt(), student.getPassword()), "密码错误");
                id = student.getId();
                role = RoleConstants.ROLE_STUDENT;
                userInfoVO.setName(student.getName());
                userInfoVO.setNumber(student.getNumber());
                break;
            // 教师登录
            case RoleConstants.ROLE_TEACHER:
                Teacher teacher = teacherService.getOne(new LambdaQueryWrapper<Teacher>()
                        .eq(Teacher::getNumber, data.getNumber()));
                Assert.isNull(teacher, "账号不存在");
                Assert.isFalse(MD5Utils.compare(data.getPassword(), teacher.getSalt(), teacher.getPassword()), "密码错误");
                id = teacher.getId();
                role = RoleConstants.ROLE_TEACHER;
                userInfoVO.setName(teacher.getName());
                userInfoVO.setNumber(teacher.getNumber());
                break;
            // 管理员登录
            case RoleConstants.ROLE_ADMIN:
                Admin admin = adminService.getOne(new LambdaQueryWrapper<Admin>()
                        .eq(Admin::getNumber, data.getNumber()));
                Assert.isNull(admin, "账号不存在");
                Assert.isFalse(MD5Utils.compare(data.getPassword(), admin.getSalt(), admin.getPassword()), "密码错误");
                id = admin.getId();
                role = RoleConstants.ROLE_ADMIN;
                userInfoVO.setName(admin.getName());
                userInfoVO.setNumber(admin.getNumber());
                break;
            default:
                throw new BusinessException("登录类型错误");
        }
        String token = tokenUtils.grantToken(id, role);
        userInfoVO.setToken(token);
        return Response.success(userInfoVO);
    }


    /**
     * [C002]退出登陆
     * GET /common/exit
     * 接口ID：54141075
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54141075
     */
    @DeleteMapping("/exit")
    @Permission({RoleConstants.ROLE_STUDENT, RoleConstants.ROLE_TEACHER, RoleConstants.ROLE_ADMIN})
    public Response<Object> exit() {
        tokenUtils.safeExit(getUid(), getRole());
        return Response.success();
    }

}

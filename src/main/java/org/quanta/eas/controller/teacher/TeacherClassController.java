package org.quanta.eas.controller.teacher;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.bean.Response;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.quanta.eas.dto.EditClassDTO;
import org.quanta.eas.dto.EditScoreDTO;
import org.quanta.eas.entity.Class;
import org.quanta.eas.entity.ClassStudent;
import org.quanta.eas.exception.ParamException;
import org.quanta.eas.exception.PermissionException;
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
@RequestMapping("/teacher/course/class")
@Permission({RoleConstants.ROLE_TEACHER})
@Api(tags = "教师端-班级模块")
public class TeacherClassController extends BaseController {
    @Autowired
    ClassService classService;
    @Autowired
    ClassStudentService classStudentService;


    /**
     * [T004]编辑班级信息
     * PUT /teacher/course/class
     * 接口ID：54139909
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54139909
     */
    @PutMapping
    @ApiOperation("[T004]编辑班级信息")
    public Response<Object> editClass(@RequestBody @Validated EditClassDTO param) {
        // 查询教师是否有课程权限
        Class one = classService.getOne(
                new LambdaQueryWrapper<Class>()
                        .eq(Class::getTeacherId, getUid())
                        .eq(Class::getId, param.getId())
                        .select(Class::getId)
        );
        if (one == null) {
            throw new PermissionException();
        }
        classService.editClass(param);
        return Response.success();
    }

    /**
     * [T005]查看课程学生列表
     * GET /teacher/course/class/{id}
     * 接口ID：54138756
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54138756
     */
    @GetMapping("/{id}")
    @ApiOperation("[T005]查看课程学生列表")
    public Response<Object> editClassStudentList(@PathVariable Long id) {
        return Response.success(classService.getClassInfo(id, getUid()));
    }

    /**
     * [T006]设置学生成绩
     * PUT /teacher/course/class/scores
     * 接口ID：54140285
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54140285
     */
    @PutMapping("/scores")
    @ApiOperation("[T006]设置学生成绩")
    public Response<Object> editScores(@RequestBody @Validated EditScoreDTO param) {
        // 查询对应班级
        ClassStudent classStudent = classStudentService.getById(param.getId());
        if (classStudent == null) {
            throw new ParamException("班级不存在");
        }
        // 校验当前教师是否有权限
        Class one = classService.getOne(
                new LambdaQueryWrapper<Class>()
                        .eq(Class::getId, classStudent.getClassId())
                        .eq(Class::getTeacherId, getUid())
        );
        if (one == null) {
            throw new PermissionException();
        }
        // 编辑学生学时
        classStudentService.editStudentScore(param);
        return Response.success();
    }
}

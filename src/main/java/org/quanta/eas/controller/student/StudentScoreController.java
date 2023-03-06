package org.quanta.eas.controller.student;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.bean.Response;
import org.quanta.eas.constants.RoleConstants;
import org.quanta.eas.controller.BaseController;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.ClassStudent;
import org.quanta.eas.mapper.ClassStudentMapper;
import org.quanta.eas.pojo.StudentClassScore;
import org.quanta.eas.service.ClassStudentService;
import org.quanta.eas.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/10
 */
@RestController
@RequestMapping("/student/score")
@Permission({RoleConstants.ROLE_STUDENT})
@Api(tags = "学生端-成绩模块")
public class StudentScoreController extends BaseController {
    @Autowired
    ClassStudentService classStudentService;

    /**
     * [S005]获取成绩列表
     * GET /student/score
     * 接口ID：54137896
     * 接口地址：https://www.apifox.cn/web/project/2064317/apis/api-54137896
     */
    @GetMapping
    @ApiOperation("[S005]获取成绩列表")
    public Response<Object> getScore(@Validated @Length(min = 9, max = 9, message = "学期格式错误") String term, @Validated PageParamDTO param) {
        PageResult studentScore = classStudentService.getStudentScore(getUid(), term, param);
        List<StudentClassScore> temp = (List<StudentClassScore>)studentScore.getDataList();
        temp.forEach(e->{
            if(e.getIsClosed().equals(0)){
                e.setUsualScore(0d);
                e.setFinalScore(0d);
                e.setScore(0d);
            };
        });
        return Response.success(studentScore);
    }


}

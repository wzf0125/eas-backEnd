package org.quanta.eas.mapper;

import org.quanta.eas.entity.ClassStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.quanta.eas.pojo.ClassInfo;
import org.quanta.eas.pojo.ClassStudentInfo;
import org.quanta.eas.pojo.StudentClassScore;
import org.quanta.eas.vo.ClassInfoVO;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wzf mqh hzq zjx
 * @since 2022-12-10
 */
@Mapper
public interface ClassStudentMapper extends BaseMapper<ClassStudent> {
    // 获取班级学生信息
    public List<ClassStudentInfo> getClassStudentInfo(Long id);

    // 获取学生成绩信息
    public List<StudentClassScore> getStudentScore(Long sid, String term, int start, int size);

    // 获取学生成绩数据
    public Long getStudentScoreCount(Long sid, String term);

    // 获取学生已选课程列表
    List<ClassInfoVO> getStudentClassList(Long id, String term, String keyWord, Integer start, Integer size);

    // 获取学生已选课程列表总数
    Long getStudentClassListCount(Long id, String term, String keyWord);

    // 获取学生课表
    List<ClassInfoVO> getStudentClassTable(Long id, String term);

    // 获取教师课表
    List<ClassInfoVO> getTeacherClassTable(Long id, String term);
}

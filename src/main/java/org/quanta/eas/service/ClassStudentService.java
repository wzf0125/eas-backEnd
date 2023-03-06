package org.quanta.eas.service;

import org.quanta.eas.dto.EditScoreDTO;
import org.quanta.eas.dto.EditStudentDTO;
import org.quanta.eas.dto.PageParamDTO;
import org.quanta.eas.entity.ClassStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quanta.eas.utils.PageResult;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wzf mqh hzq zjx
 * @since 2022-12-10
 */
public interface ClassStudentService extends IService<ClassStudent> {
    // 获取学生成绩
    public PageResult getStudentScore(Long sid, String term, PageParamDTO param);

    // 编辑学生成绩
    public void editStudentScore(EditScoreDTO param);
}

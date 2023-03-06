package org.quanta.eas.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.List;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/15
 */
@Data
public class CourseMajorInfo {
    /**
     * pk_id
     */
    private Long id;

    /**
     * 可选课年级
     */
    private List<String> year;

    /**
     * 学院名称
     */
    private String major;
}

package org.quanta.eas.vo;

import lombok.Data;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/12
 */
@Data
public class StudentInfoVO {
    /**
     * pk_id
     */
    private Long id;

    /**
     * 教师姓名
     */
    private String name;

    /**
     * 工号
     */
    private String number;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐值
     */
    private String salt;

    /**
     * 学院名称
     */
    private String major;

    /**
     * 年假
     */
    private String year;
}

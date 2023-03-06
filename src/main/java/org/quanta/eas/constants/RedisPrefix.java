package org.quanta.eas.constants;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/10
 */
public class RedisPrefix {
    public static final String PROJECT_NAME = "eas_"; // 项目前缀
    public static final String STUDENT_TOKEN_PREFIX = PROJECT_NAME + "student_%s"; // 学生token
    public static final String TEACHER_TOKEN_PREFIX = PROJECT_NAME + "teacher_%s"; // 教师token
    public static final String ADMIN_TOKEN_PREFIX = PROJECT_NAME + "admin_%s"; // 管理员token
    public static final String CLASS_CAPACITY_PREFIX = PROJECT_NAME + "class_capacity_%s"; // 班级剩余人数
//    public static final String CHOICE_COURSE_LOCK = PROJECT_NAME+"choice_course_lock"; // 选课锁
}

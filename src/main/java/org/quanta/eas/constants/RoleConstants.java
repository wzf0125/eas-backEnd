package org.quanta.eas.constants;

import org.quanta.eas.exception.BusinessException;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/11
 */
public class RoleConstants {
    public static final int ROLE_STUDENT = 0;
    public static final int ROLE_TEACHER = 1;
    public static final int ROLE_ADMIN = 2;

    public static String getRolePrefix(Integer role) {
        switch (role) {
            case ROLE_STUDENT:
                return RedisPrefix.STUDENT_TOKEN_PREFIX;
            case ROLE_TEACHER:
                return RedisPrefix.TEACHER_TOKEN_PREFIX;
            case ROLE_ADMIN:
                return RedisPrefix.ADMIN_TOKEN_PREFIX;
            default:
                throw new BusinessException();
        }
    }
}

package org.quanta.eas.utils;

import org.apache.commons.lang3.StringUtils;
import org.quanta.eas.exception.BusinessException;

import java.util.Collection;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/11
 */
public class Assert {
    public static void isBlank(String str, String msg) {
        if (StringUtils.isBlank(str)) {
            throw new BusinessException(msg);
        }
    }

    public static void isNull(Object object, String msg) {
        if (object == null) {
            throw new BusinessException(msg);
        }
    }

    public static void isNull(String msg, String... param) {
        for (String s : param) {
            if (s == null) {
                throw new BusinessException(msg);
            }
        }
    }

    public static void isEmpty(Collection<Object> collection, String msg) {
        if (collection == null || collection.isEmpty()) {
            throw new BusinessException(msg);
        }
    }

    public static void isFalse(Boolean test, String msg) {
        if (!test) {
            throw new BusinessException(msg);
        }
    }

    public static void isTrue(Boolean test, String msg) {
        if (test) {
            throw new BusinessException(msg);
        }
    }
}

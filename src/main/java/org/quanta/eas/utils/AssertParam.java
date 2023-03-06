/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package org.quanta.eas.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.BuilderException;
import org.quanta.eas.exception.ParamException;

import java.text.ParseException;
import java.util.Collection;

/**
 * Description: 断言类
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/10
 */
public abstract class AssertParam {

    public static void isBlank(String str, String msg) {
        if (StringUtils.isBlank(str)) {
            throw new ParamException(msg);
        }
    }

    public static void isNull(Object object, String msg) {
        if (object == null) {
            throw new ParamException(msg);
        }
    }

    public static void isNull(String msg, Object... param) {
        for (Object o : param) {
            if (o == null) {
                throw new ParamException(msg);
            }
        }
    }

    public static void isEmpty(Collection<Object> collection, String msg) {
        if (collection == null || collection.isEmpty()) {
            throw new ParamException(msg);
        }
    }

    public static void isFalse(Boolean test, String msg) {
        if (!test) {
            throw new ParamException(msg);
        }
    }

    public static void isTrue(Boolean test, String msg) {
        if (!test) {
            throw new ParamException(msg);
        }
    }
}

package org.quanta.eas.exception;

/**
 * Description: 业务异常类 当出现业务异常时直接抛出返回请求失败 减少各层直接错误信息层层上报的繁琐
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/10
 */
public class BusinessException extends RuntimeException {
    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }
}

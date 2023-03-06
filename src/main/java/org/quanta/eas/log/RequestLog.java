package org.quanta.eas.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.quanta.eas.bean.AuthInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * Description: 日志切面注入类
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/10
 */
@Aspect
@Component
public class RequestLog {
    // 加载日志工厂
    private final Logger logger = LoggerFactory.getLogger(getClass());

    // 请求日志模板
    private static final String BEFORE_LOG_TEMPLATE = "REQUEST_LOG: LogId: {} | Request URL: {} | Http Method: {} | URL: {} | IP: {} | Class Method: {} | Args: {} | Token: {} | Msg: {}";
    // 请求返回日志模板
    private static final String AFTER_LOG_TEMPLATE = "RESULT_LOG: Token: {} | Uid: {} | Result: {} ";

    @Pointcut("@within(org.quanta.eas.annotation.Log)")
    public void cutPoint() {
        // 拦截控制器所有方法
    }

    @Before("cutPoint()")
    public void doBefore(JoinPoint joinPoint) {
        String logId = String.valueOf(UUID.randomUUID());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            request.setAttribute("requestId", logId);
            String args = Arrays.toString(joinPoint.getArgs());
            request.setAttribute("args", args);
            logger.info(BEFORE_LOG_TEMPLATE,
                    logId,
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    request.getRemoteAddr(),
                    request.getHeader("X-Real-IP"),
                    joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                    args,
                    request.getHeader("Token"),
                    "");
        } else {
            logger.info(BEFORE_LOG_TEMPLATE, logId, "", "", "", "", "", "", "", "Empty Request!");
        }
    }

    @AfterReturning(returning = "result", pointcut = "cutPoint()")
    public void doAfter(Object result) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        long uid = 0L;
        String token = null;
        if (attributes != null) {
            token = attributes.getRequest().getHeader(AuthInterceptor.TOKEN_HEADER);
            try {
                uid = (long) attributes.getRequest().getAttribute("uid");
            }catch (Exception ignored){
            }
        }
        logger.info(AFTER_LOG_TEMPLATE, token, uid, result);
    }
}

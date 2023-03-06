package org.quanta.eas.bean;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quanta.eas.annotation.Permission;
import org.quanta.eas.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: 返回值枚举类型
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/9
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    public static final String TOKEN_HEADER = "Authorization";

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是否是OPTIONS 是则放行
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            log.info("OPTIONS: ");
            return true;
        }

        // 设置响应头为json
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        // 设置响应头编码
        response.setCharacterEncoding("UTF-8");
        // 无token参数拒绝，放通的接口除外
        if (request.getHeader(TOKEN_HEADER) == null || request.getHeader(TOKEN_HEADER).isEmpty()) {
            log.debug("");
            response.getWriter().write(JSON.toJSONString(Response.unauthorized("缺少token参数")));
            return false;
        }
        String token = request.getHeader(TOKEN_HEADER);
        if (token == null) {
            response.getWriter().write(JSON.toJSONString(Response.unauthorized()));
        }
        int role;
        try {
            role = tokenUtils.getTokenRole(token);
        } catch (Exception e) {
            response.getWriter().write(JSON.toJSONString(Response.unauthorized(e.getMessage())));
            return false;
        }
        if (this.hasPermission(handler, role)) {
            long uid = tokenUtils.getTokenUid(token);
            // 刷新token
            tokenUtils.refreshToken(token, uid, role);
            // 权限塞request里传给controller
            request.setAttribute("uid", uid);
            request.setAttribute("role", role);
            return true;
        }
        // 没通过权限验证 返回权限不足
        response.getWriter().write(JSON.toJSONString(Response.permissionDenied()));
        return false;
    }

    /**
     * 权限验证方法
     */
    private boolean hasPermission(Object handler, int role) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Permission permission = handlerMethod.getMethod().getAnnotation(Permission.class);
            if (permission == null) {
                permission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Permission.class);
            }
            if (permission != null) {
                for (int p : permission.value()) {
                    if (role == p) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

}

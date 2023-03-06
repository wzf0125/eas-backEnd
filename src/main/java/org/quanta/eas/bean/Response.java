package org.quanta.eas.bean;

import org.quanta.eas.constants.ResultCode;

/**
 * Description: 全局响应类
 * Param:
 * return:
 * Author: wzf mqh hzq zjx
 * Date: 2022/12/9
 */
public class Response<T> {
    private Integer code;
    private String msg;
    private T data;

    public Response(T data) {
        this.data = data;
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 操作成功
     */
    public static <T> Response<T> success() {
        return new Response<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
    }

    /**
     * 操作成功
     */
    public static <T> Response<T> success(T data) {
        return new Response<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }

    /**
     * 操作成功
     */
    public static <T> Response<T> success(T data, String msg) {
        return new Response<>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 操作失败
     */
    public static <T> Response<T> fail() {
        return new Response<>(ResultCode.FAIL.getCode(), ResultCode.FAIL.getMsg());
    }

    /**
     * 操作失败 自定义错误信息
     */
    public static <T> Response<T> fail(String msg) {
        return new Response<>(ResultCode.FAIL.getCode(), msg);
    }

    /**
     * 无权限
     */
    public static <T> Response<T> permissionDenied() {
        return new Response<>(ResultCode.PERMISSION_DENIED.getCode(), ResultCode.PERMISSION_DENIED.getMsg());
    }

    /**
     * 无权限
     */
    public static <T> Response<T> permissionDenied(String msg) {
        return new Response<>(ResultCode.PERMISSION_DENIED.getCode(), msg);
    }

    /**
     * 请先登录
     */
    public static <T> Response<T> unauthorized() {
        return new Response<>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMsg());
    }

    /**
     * 请先登录
     */
    public static <T> Response<T> unauthorized(String msg) {
        return new Response<>(ResultCode.UNAUTHORIZED.getCode(), msg);
    }

    /**
     * 参数错误
     */
    public static <T> Response<T> paramError() {
        return new Response<>(ResultCode.PARAM_ERROR.getCode(), ResultCode.PARAM_ERROR.getMsg());
    }

    /**
     * 参数错误
     */
    public static <T> Response<T> paramError(String msg) {
        return new Response<>(ResultCode.PARAM_ERROR.getCode(), msg);
    }

    /**
     * 服务器错误
     */
    public static <T> Response<T> serverError() {
        return new Response<>(ResultCode.SERVER_ERROR.getCode(), ResultCode.SERVER_ERROR.getMsg());
    }

    /**
     * 服务器错误
     */
    public static <T> Response<T> serverError(String msg) {
        return new Response<>(ResultCode.SERVER_ERROR.getCode(), msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}

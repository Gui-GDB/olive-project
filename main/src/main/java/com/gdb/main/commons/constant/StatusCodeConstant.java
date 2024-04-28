package com.gdb.main.commons.constant;

/**
 * 返回状态的常量（包括响应的状态码和账号状态码）
 *
 * @author Mr.Gui
 * */

public class StatusCodeConstant {
    /**
     * 参数不正确，发生异常
     */
    public static final Integer ERROR = 0;
    /**
     * 登录相关的异常
     */
    public static final Integer LOGIN_EXCEPTION = -1;
    /**
     * 前端传递的参数的异常
     */
    public static final Integer PARAM_EXCEPTION = -3;
    /**
     * 表示用户的状态为不可用
     */
    public static final Byte DISABLE = 0;
    /**
     * 表示用户的状态为可用
     */
    public static final Byte ABLE = 1;
}

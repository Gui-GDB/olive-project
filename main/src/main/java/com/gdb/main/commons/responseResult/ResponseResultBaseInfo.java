package com.gdb.main.commons.responseResult;

/**
 * 响应信息的基础接口, 提高程序的耦合度
 *
 * @author Mr.Gui
 * */

public interface ResponseResultBaseInfo {
    /**
     * 获取错误码
     * @return 错误码
     */
    Integer getResultCode();

    /**
     * 获取错误描述
     * @return 错误描述
     */
    String getResultMsg();
}

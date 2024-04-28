package com.gdb.main.commons.responseResult;

/**
 * 异常处理枚举类
 *
 * @author Mr.Gui
 */
public enum ResponseEnum implements ResponseResultBaseInfo {

    // 数据操作定义
    UNKNOWN_ERROR(-1, "未知错误"),
    ERROR(0, "失败"),
    SUCCESS(1, "成功"),
    TOKEN_ERROR(2, "token无效"),
    ALGORITHM_MISMATCH(3, "token算法不一致"),
    TOKEN_EXPIRED(4, "token已过期"),
    SIGNATURE_VERIFICATION(5, "token签名无效"),
    PASSWORD_ERROR(6, "密码错误"),
    ACCOUNT_NOT_FOUND(7, "账号不存在"),
    ACCOUNT_LOCKED(8, "账号被锁定"),
    PARAM_CHOOSE_ERROR(9, "choose参数值不符合要求，请输入1或者2"),
    IMAGE_NOT_SUFFIX(10, "文件没有后缀名"),
    UPLOAD_FAIL(11, "文件上传失败"),
    IMAGE_NO_GPS_INFO(12, "该图片没有GPS定位信息"),
    IMAGE_TYPE_ERROR(13, "上传图片格式错误"),
    IMAGE_SIZE_OVER(14, "图片太大");

    /**
     * 错误码
     */
    private final Integer resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    ResponseEnum(Integer resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }


    @Override
    public Integer getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}

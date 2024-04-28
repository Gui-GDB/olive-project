package com.gdb.main.commons.responseResult;

import com.gdb.main.commons.constant.StatusCodeConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 自定义数据传输
 */
@Data
public class ResponseResult <T> {
    /**
     * 响应代码
     */
    @Schema(description = "响应状态码，请查看约定的状态码信息表")
    private Integer code;

    /**
     * 响应消息
     */
    @Schema(description = "响应说明")
    private String message;

    /**
     * 响应结果
     */
    @Schema(description = "响应内容")
    private T data;
    public ResponseResult(){

    }

    public ResponseResult(ResponseResultBaseInfo res) {
        this.code = res.getResultCode();
        this.message = res.getResultMsg();
    }

    /**
     * 成功
     */
    public static <H> ResponseResult<H> success() {
        return success(null);
    }

    /**
     * 成功
     */
    public static <H> ResponseResult<H> success(H data) {
        ResponseResult<H> responseResult = new ResponseResult<>();
        responseResult.setCode(ResponseEnum.SUCCESS.getResultCode());
        responseResult.setMessage(ResponseEnum.SUCCESS.getResultMsg());
        responseResult.setData(data);
        return responseResult;
    }

    /**
     * 失败
     */
    public static <H> ResponseResult<H> error(ResponseResultBaseInfo res) {
        ResponseResult<H> responseResult = new ResponseResult<>();
        responseResult.setCode(res.getResultCode());
        responseResult.setMessage(res.getResultMsg());
        responseResult.setData(null);
        return responseResult;
    }

    /**
     * 失败
     */
    public static <H> ResponseResult<H> error(Integer code, String message) {
        ResponseResult<H> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setData(null);
        return responseResult;
    }

    /**
     * 失败
     */
    public static <H> ResponseResult<H> error(String message) {
        ResponseResult<H> responseResult = new ResponseResult<>();
        responseResult.setCode(StatusCodeConstant.ERROR);
        responseResult.setMessage(message);
        responseResult.setData(null);
        return responseResult;
    }

}

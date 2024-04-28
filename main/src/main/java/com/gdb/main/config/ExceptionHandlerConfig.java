package com.gdb.main.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.gdb.main.commons.exception.*;
import com.gdb.main.commons.responseResult.ResponseEnum;
import com.gdb.main.commons.responseResult.ResponseResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author Mr.Gui
 */
@RestControllerAdvice
public class ExceptionHandlerConfig {
    /**
     * 空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseResult<String> nullPointException(NullPointerException e, HttpServletResponse response) {
        e.printStackTrace();
        //设置状态响应码为 500
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return ResponseResult.error(e.getMessage());
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<String> exception(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        //设置状态响应码为 500
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return ResponseResult.error(ResponseEnum.ERROR);
    }


    /**
     * 用户登录相关的异常
     */
    @ExceptionHandler({AccountNotFoundException.class, AccountPasswordErrorException.class, AccountLockedException.class})
    public ResponseResult<String> loginException(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        //设置状态响应码为 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (e instanceof AccountNotFoundException) {
            //账户不存在异常
            return ResponseResult.error(ResponseEnum.ACCOUNT_NOT_FOUND);
        }
        if (e instanceof AccountPasswordErrorException) {
            //密码错误异常
            return ResponseResult.error(ResponseEnum.PASSWORD_ERROR);
        }
        if (e instanceof AccountLockedException) {
            //账户状态锁定异常
            return ResponseResult.error(ResponseEnum.ACCOUNT_LOCKED);
        }
        return ResponseResult.error(ResponseEnum.UNKNOWN_ERROR);
    }

    /**
     * token 相关的异常
     */
    @ExceptionHandler({JWTDecodeException.class, TokenExpiredException.class, SignatureVerificationException.class, AlgorithmMismatchException.class})
    public ResponseResult<String> tokenException(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        // 设置响应状态码为 401
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (e instanceof JWTDecodeException) {
            // token无效异常
            return ResponseResult.error(ResponseEnum.TOKEN_ERROR);
        }
        if (e instanceof TokenExpiredException) {
            //token过期异常
            return ResponseResult.error(ResponseEnum.TOKEN_EXPIRED);
        }
        if (e instanceof SignatureVerificationException) {
            // token签名无效
            return ResponseResult.error(ResponseEnum.SIGNATURE_VERIFICATION);
        }
        if (e instanceof AlgorithmMismatchException) {
            // token算法不一致
            return ResponseResult.error(ResponseEnum.ALGORITHM_MISMATCH);
        }
        return ResponseResult.error(ResponseEnum.UNKNOWN_ERROR);
    }

    /**
     * 病虫害识别异常
     */
    @ExceptionHandler(IdentifyBugChooseTypeException.class)
    public ResponseResult<String> identifyException(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        //设置响应状态码为400
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        //虫害识别的choose参数错误
        return ResponseResult.error(ResponseEnum.PARAM_CHOOSE_ERROR);

    }

    /**
     * 图片处理相关的异常
     */
    @ExceptionHandler({ImageNoGPSInfoException.class, ImageTypeErrorException.class, ImageSizeOverException.class})
    public ResponseResult<String> noGPSInfoException(Exception e, HttpServletResponse response) {
        e.printStackTrace();
        if(e instanceof ImageNoGPSInfoException) {
            //图片没有 GPS 信息异常。
            return ResponseResult.error(ResponseEnum.IMAGE_NO_GPS_INFO);
        }
        //图片太大
        if(e instanceof ImageSizeOverException){
            return ResponseResult.error(ResponseEnum.IMAGE_SIZE_OVER);
        }
        //上传图片后缀名异常

        return ResponseResult.error(ResponseEnum.IMAGE_TYPE_ERROR);
    }
}

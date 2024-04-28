package com.gdb.main.commons.exception;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-23 15:26
 * @description: 图片上传格式异常
 **/

public class ImageTypeErrorException extends RuntimeException{
    public ImageTypeErrorException() {
        super();
    }

    public ImageTypeErrorException(String message) {
        super(message);
    }
}

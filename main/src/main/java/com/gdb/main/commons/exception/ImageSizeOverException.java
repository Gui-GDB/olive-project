package com.gdb.main.commons.exception;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-23 18:15
 * @description: 图像太大异常
 **/

public class ImageSizeOverException extends RuntimeException{
    public ImageSizeOverException(String message) {
        super(message);
    }

    public ImageSizeOverException() {
    }
}

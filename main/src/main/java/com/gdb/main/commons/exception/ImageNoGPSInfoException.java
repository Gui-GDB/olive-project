package com.gdb.main.commons.exception;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-04-19 21:02
 * @description:
 **/

public class ImageNoGPSInfoException extends RuntimeException{
    public ImageNoGPSInfoException(String message) {
        super(message);
    }

    public ImageNoGPSInfoException() {
        super();
    }
}

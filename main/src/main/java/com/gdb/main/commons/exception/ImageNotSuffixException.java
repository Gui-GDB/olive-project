package com.gdb.main.commons.exception;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-31 21:11
 * @description:
 **/

public class ImageNotSuffixException extends RuntimeException{
    public ImageNotSuffixException() {
        super();
    }

    public ImageNotSuffixException(String message) {
        super(message);
    }
}

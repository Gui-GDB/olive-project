package com.gdb.main.commons.exception;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-18 19:14
 * @description: 账户密码错误异常
 **/

public class AccountPasswordErrorException extends RuntimeException{
    public AccountPasswordErrorException(String message) {
        super(message);
    }

    public AccountPasswordErrorException() {
    }

}

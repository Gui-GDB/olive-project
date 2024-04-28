package com.gdb.main.commons.exception;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-18 19:08
 * @description: 账户不存在异常
 **/

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}

package com.gdb.main.commons.exception;

/**
 * @author: Mr.Gui
 * @program: olive-project
 * @create: 2024-03-18 19:15
 * @description: 账户处于锁定状态异常
 **/

public class AccountLockedException extends RuntimeException{
    public AccountLockedException() {
    }

    public AccountLockedException(String message) {
        super(message);
    }
}

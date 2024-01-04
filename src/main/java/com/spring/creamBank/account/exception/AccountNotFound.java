package com.spring.creamBank.account.exception;

import java.util.HashMap;
import java.util.Map;

public class AccountNotFount  extends HeadException{

    private static final String MESSAGE = "계좌가 존재하지 않습니다.";

    public AccountNotFount(String message) {
        super(MESSAGE);
    }

    public AccountNotFount(String message, Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() { return 404;}

}

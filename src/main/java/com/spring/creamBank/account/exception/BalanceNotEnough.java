package com.spring.creamBank.account.exception;

public class BalanceNotEnough extends HeadException{

    private static final String MESSAGE = "잔고가 부족합니다.";

    public BalanceNotEnough() {
        super(MESSAGE);
    }

    public BalanceNotEnough(String message) {
        super(MESSAGE);
    }

    public BalanceNotEnough(String message, Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() { return 403;}

}

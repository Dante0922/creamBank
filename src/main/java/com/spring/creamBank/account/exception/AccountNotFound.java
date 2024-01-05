package com.spring.creamBank.account.exception;

public class AccountNotFound extends HeadException{

    private static final String MESSAGE = "계좌가 존재하지 않습니다.";

    public AccountNotFound() {
        super(MESSAGE);
    }

    public AccountNotFound(String message) {
        super(MESSAGE);
    }

    public AccountNotFound(String message, Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() { return 404;}

}

package com.spring.creamBank.account.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class HeadException  extends RuntimeException{

    private final Map<String, String> validation = new HashMap<>();

    public HeadException(String message) {
        super(message);
    }

    public HeadException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message){
        validation.put(fieldName, message);
    }
}

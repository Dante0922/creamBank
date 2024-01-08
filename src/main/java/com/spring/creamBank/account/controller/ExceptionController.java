package com.spring.creamBank.account.controller;

import com.spring.creamBank.account.dto.ErrorResponse;
import com.spring.creamBank.account.exception.HeadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invaildRequestHandler(MethodArgumentNotValidException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .validation(new HashMap<>())
                .build();

        for(FieldError fieldError : e.getFieldErrors()){
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errorResponse;
    }

    @ResponseBody
    @ExceptionHandler(HeadException.class)
    public ResponseEntity<ErrorResponse> headException(HeadException e){

        int statusCode = e.getStatusCode();

        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        return ResponseEntity.status(statusCode)
                .body(response);

    }
}

package com.sample.springjwt.controllers;

import com.sample.springjwt.exceptions.AppException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> handleAppException(AppException exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();

        return super.handleExceptionInternal(exception,
                exception.getAppMsg(),
                headers,
                HttpStatus.BAD_REQUEST,
                request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object object,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return super.handleExceptionInternal(ex, "エラー", headers, status, request);
    }
}

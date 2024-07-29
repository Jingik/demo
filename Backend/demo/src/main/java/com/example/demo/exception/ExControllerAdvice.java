package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestControllerAdvice("com.example.demo")
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResult HttpNotReadableExHandle(HttpMessageNotReadableException e) {
        log.info("[HttpNotReadableExHandle] = {}", e.getMessage());
        return new ErrorResult("400 BAD_REQUEST", "잘못된 입력 값 또는 요청 형식");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult ArgumentNotValidExHandle(MethodArgumentNotValidException e) {
        log.info("[ArgumentNotValidExHandle] = {}", e.getMessage());
        return new ErrorResult("400 BAD_REQUEST", "필수 값이 비어있음");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResult TypeMismatchExHandle(MethodArgumentTypeMismatchException e) {
        log.info("[TypeMismatchExHandle] = {}", e.getMessage());
        return new ErrorResult("400 BAD_REQUEST", "잘못된 요청 경로");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResult ResponseStatusExHandle(ResponseStatusException e) {
        log.info("[ResponseStatusExHandle] = {}", e.getMessage());
        return new ErrorResult(e.getStatusCode().toString(), e.getMessage());
    }

}

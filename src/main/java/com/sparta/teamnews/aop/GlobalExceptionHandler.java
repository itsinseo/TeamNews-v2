package com.sparta.teamnews.aop;

import com.sparta.teamnews.service.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.RejectedExecutionException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class, RejectedExecutionException.class})
    public ResponseEntity<ApiResponseDto> exceptionHandler(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value())
        );
    }
}

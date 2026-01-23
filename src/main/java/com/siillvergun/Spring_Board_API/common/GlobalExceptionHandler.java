package com.siillvergun.Spring_Board_API.common;

import com.siillvergun.Spring_Board_API.user.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ComstomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(ComstomException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error("Custom Error: {} - {}", errorCode.getCode(), errorCode.getMessage());

        ErrorResponse response = ErrorResponse.of(e.getErrorCode(), e.getMessage());

        // ErrorCode의 첫 두 글자가 40이면 404, 아니면 400 등 유연하게 처리 가능
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}

package com.siillvergun.Spring_Board_API.global.error;

import com.siillvergun.Spring_Board_API.global.error.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // "이 서버의 모든 컨트롤러에서 발생하는 에러는 이 클래스가 관리
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class) // 특히 CustomException이 날라오면 잡는다
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        // 잡은 에러 상자(e)를 열어서 안에 든 ErrorCode를 꺼냄
        ErrorCode errorCode = e.getErrorCode();

        // 에러 로그 남김
        log.error("Custom Error: {} - {}", errorCode.getCode(), errorCode.getMessage());

        // 에러를 Dto에 담는다
        ErrorResponseDto response = ErrorResponseDto.of(e.getErrorCode());

        // Http 상태 코드를 JSON 데이터의 응답으로 보냄
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }
}

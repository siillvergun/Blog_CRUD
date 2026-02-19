package com.siillvergun.Spring_Board_API.global.error.dto;

import com.siillvergun.Spring_Board_API.global.error.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponseDto {
    private final String errorcode;
    private final String message;

    public static ErrorResponseDto of(ErrorCode errorCode) {
        return ErrorResponseDto.builder()
                .errorcode(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}

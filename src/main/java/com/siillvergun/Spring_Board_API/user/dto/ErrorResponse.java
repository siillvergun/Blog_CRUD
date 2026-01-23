package com.siillvergun.Spring_Board_API.user.dto;

import com.siillvergun.Spring_Board_API.common.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final String errorcode;
    private final String message;

    public static ErrorResponse of(ErrorCode errorCode, String detail) {
        return ErrorResponse.builder()
                .errorcode(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }
}

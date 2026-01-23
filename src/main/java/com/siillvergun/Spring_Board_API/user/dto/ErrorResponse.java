package com.siillvergun.Spring_Board_API.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private final String errorcode;
    private final String message;
    private final String detail;
}

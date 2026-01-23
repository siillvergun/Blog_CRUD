package com.siillvergun.Spring_Board_API.common;

import lombok.Getter;

@Getter
public class ComstomException extends RuntimeException {
    private final ErrorCode errorCode;

    public ComstomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

class UserNotFoundException extends ComstomException {
    public UserNotFoundException(String message) {
        super(ErrorCode.USER_NOT_FOUND);
    }
}

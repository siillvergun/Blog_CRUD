package com.siillvergun.Spring_Board_API.common;

public class ComstomException extends RuntimeException {
    public ComstomException(String message) {
        super(message);
    }
}

class UserNotFoundException extends ComstomException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

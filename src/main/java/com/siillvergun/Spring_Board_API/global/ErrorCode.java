package com.siillvergun.Spring_Board_API.global;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("4040", "유저를 찾을 수 없습니다."),
    POST_NOT_FOUND("4041", "게시글을 찾을 수 없습니다."),
    UNAUTHORIZED_ACCESS("4042", "허용되지 않은 접근입니다.");
    private final String code;
    private final String message;
}

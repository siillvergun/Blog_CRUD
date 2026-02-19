package com.siillvergun.Spring_Board_API.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
Http상태 및 메시지를 가지고 있는 에러 코드 상수
에러 처리 가장 기본이 되는 에러들을 커스텀하는 클래스
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("4040", "유저를 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    POST_NOT_FOUND("4041", "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    UNAUTHORIZED_ACCESS("4042", "허용되지 않은 접근입니다.", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}

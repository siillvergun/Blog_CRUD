package com.siillvergun.Spring_Board_API.user.dto;

import com.siillvergun.Spring_Board_API.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    // 가입 시 필요한 딱 3가지 정보만 정의, id는 스프링에서 자동으로 생성
    private String email;
    private String nickname;
    private String password;

    // DTO를 엔티티로 변환해주는 편의 메서드
    // from()이랑 마찬가지
    public User toEntity(String encodedPassword) {
        return User.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(encodedPassword) // 서비스 계층에서 암호화된 비밀번호를 전달받아 엔티티 생성
                .build();
    }
}
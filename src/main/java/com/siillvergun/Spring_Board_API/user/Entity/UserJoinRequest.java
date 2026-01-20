package com.siillvergun.Spring_Board_API.user.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRequest {
    // 가입 시 필요한 딱 3가지 정보만 정의
    private String email;
    private String nickname;
    private String password;

    // DTO를 엔티티로 변환해주는 편의 메서드
    public User toEntity(String encodedPassword) {
        return User.builder()
                .email(this.email)
                .nickname(this.nickname)
                .password(encodedPassword) // 실제로는 암호화 후 저장해야 합니다.
                .build();
    }
}

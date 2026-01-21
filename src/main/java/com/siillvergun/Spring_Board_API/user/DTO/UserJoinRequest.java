package com.siillvergun.Spring_Board_API.user.DTO;

import com.siillvergun.Spring_Board_API.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor // 스프링이 JSON 데이터를 자바 객체(DTO)로 변환할 때(Jackson 라이브러리), 기본 생성자를 사용해 객체를 먼저 생성
@AllArgsConstructor // 빌더를 쓰려면 얘가 있어야함
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
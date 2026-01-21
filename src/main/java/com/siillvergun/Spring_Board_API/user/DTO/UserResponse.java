package com.siillvergun.Spring_Board_API.user.DTO;

import com.siillvergun.Spring_Board_API.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String email;
    private String nickname;

    // 엔티티를 DTO로 변환해주는 편의 메서드
    // 이게 없으면 일일이 빌더로 수동 매핑해줘야 하는데 메서드로 만듬으로써 코드를 깔끔하게 유지 가능
    public static UserResponse toDTO(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}

package com.siillvergun.Spring_Board_API.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserProfileUpdateRequest {
    private String email;
    private String nickname;
}

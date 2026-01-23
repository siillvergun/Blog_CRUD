package com.siillvergun.Spring_Board_API.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateRequest {
    private String email;
    private String nickname;
}

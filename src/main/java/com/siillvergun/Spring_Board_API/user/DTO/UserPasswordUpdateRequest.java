package com.siillvergun.Spring_Board_API.user.DTO;

import lombok.Getter;

@Getter
public class UserPasswordUpdateRequest {
    private String currentPassword; // 현재 비밀번호 (본인 확인용)
    private String newPassword;     // 새로 바꿀 비밀번호
}

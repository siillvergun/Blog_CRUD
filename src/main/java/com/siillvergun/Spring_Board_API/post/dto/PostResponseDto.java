package com.siillvergun.Spring_Board_API.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostResponseDto {
    private String title;
    private String content;
    private byte img;
    private String nickname;
    private Long userId;
}

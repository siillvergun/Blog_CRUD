package com.siillvergun.Spring_Board_API.post.dto;


import com.siillvergun.Spring_Board_API.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor // 스프링이 JSON 데이터를 자바 객체(DTO)로 변환할 때(Jackson 라이브러리), 기본 생성자를 사용해 객체를 먼저 생성
@AllArgsConstructor // @Builder는 모든 인자 생성자가 필요함
public class PostResponseDto {
    private String title;
    private String content;
    private String img;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .img(post.getImg())
                .build();
    }
}

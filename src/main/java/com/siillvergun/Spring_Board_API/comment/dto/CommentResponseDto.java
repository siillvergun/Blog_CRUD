package com.siillvergun.Spring_Board_API.comment.dto;

import com.siillvergun.Spring_Board_API.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String title;
    private String content;
    private String authorNickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CommentResponseDto from(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .title(comment.getPost().getTitle())
                .authorNickname(comment.getAuthor().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt()).build();
    }
}

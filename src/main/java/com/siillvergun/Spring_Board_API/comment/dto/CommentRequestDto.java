package com.siillvergun.Spring_Board_API.comment.dto;

import com.siillvergun.Spring_Board_API.comment.entity.Comment;
import com.siillvergun.Spring_Board_API.post.entity.Post;
import com.siillvergun.Spring_Board_API.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private String content;

    public Comment toEntity(User author, Post post) {
        return Comment.builder()
                .author(author)
                .post(post)
                .content(this.content)
                .build();
    }
}

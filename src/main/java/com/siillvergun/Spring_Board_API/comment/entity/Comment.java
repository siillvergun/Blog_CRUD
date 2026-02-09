package com.siillvergun.Spring_Board_API.comment.entity;

import com.siillvergun.Spring_Board_API.global.BaseEntity;
import com.siillvergun.Spring_Board_API.post.entity.Post;
import com.siillvergun.Spring_Board_API.user.entity.User;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity {
    private Post post;
    private User author;
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

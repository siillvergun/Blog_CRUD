package com.siillvergun.Spring_Board_API.comment.entity;

import com.siillvergun.Spring_Board_API.global.BaseEntity;
import com.siillvergun.Spring_Board_API.post.entity.Post;
import com.siillvergun.Spring_Board_API.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(length = 100)
    private String content;

    @Builder
    public Comment(String content, Post post, User author) {
        this.author = author;
        this.post = post;
        this.content = content;
    }

    public void changeComment(String content) {
        if (content != null || content.isBlank())
            this.content = content;
    }
}

package com.siillvergun.Spring_Board_API.comment;

import com.siillvergun.Spring_Board_API.post.Post;
import com.siillvergun.Spring_Board_API.user.Entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Comment {
    private Post post;
    private User author;
    private Long commentId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Comment() {

    }

    // 1. 새로운 댓글을 "처음" 만들 때 쓰는 생성자
    public Comment(Post post, User author, Long commentId, String content) {
        this.post = post;
        this.author = author;
        this.commentId = commentId;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 2. 이미 존재하는 데이터(DB 등)를 "불러올" 때 쓰는 생성자
    public Comment(Post post, User author, Long commentId, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.post = post;
        this.author = author;
        this.commentId = commentId;
        this.content = content;
        this.createdAt = createdAt; // 외부에서 받은 시간을 그대로 사용
        this.updatedAt = updatedAt;
    }

    public void assignCommentId(Long id) {
        this.commentId = id;
    }

}

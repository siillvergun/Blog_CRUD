package com.siillvergun.Spring_Board_API.post;

import com.siillvergun.Spring_Board_API.user.Entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Post {
    private Long postId;
    // ERD를 보면 외래키가 숫자이기 때문에 userId를 받아와야하는게 아닌가 생각했지만
    // 자바 코드 상으로는 객체를 중심으로 생각하는게 더 좋음
    // 이렇게 작성하면 다른 모든 정보에 접근 가능
    private User author;
    private String title;
    private String content;
    private String img;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Post() {
    }

    // 1. 새로운 게시글을 "처음" 만들 때 쓰는 생성자
    public Post(Long postId, User author, String title, String content, String img) {
        this.postId = postId;
        this.author = author;
        this.title = title;
        this.content = content;
        this.img = img;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 2. 이미 존재하는 데이터(DB 등)를 "불러올" 때 쓰는 생성자
    public Post(Long postId, User author, String title, String content, String img, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.postId = postId;
        this.author = author;
        this.title = title;
        this.content = content;
        this.img = img;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void assignPostId(Long id) {
        this.postId = id;
    }

}
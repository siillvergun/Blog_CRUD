package com.siillvergun.Spring_Board_API.post.entity;

import com.siillvergun.Spring_Board_API.common.BaseEntity;
import com.siillvergun.Spring_Board_API.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity {
    // ERD를 보면 외래키가 숫자이기 때문에 userId를 받아와야하는게 아닌가 생각했지만
    // 자바 코드 상으로는 객체를 중심으로 생각하는게 더 좋음
    // 이렇게 작성하면 다른 모든 정보에 접근 가능
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User author;

    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 1000, nullable = false)
    private String content;
    @Column(length = 200)
    private String img;
}
package com.siillvergun.PostBoardTest.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter // 각 필드값을 가져오는 메서드 자동 생성
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 가지는 생성자
@Entity // 이 클래스를 Entity로 지정
@Table(name = "users") // sql예약어에 USER가 있어서 테이블명을 이렇게 지정해줘야한다. 그냥 USER는 사용 불가
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 원래 ++seq했던걸 이 어노테이션을 통해 자동으로 증가하게 만들 수 있음
    private Long userId;
    @Column(length = 40, nullable = false, unique = true)
    private String email;
    @Column(length = 15, nullable = false, unique = true)
    private String nickname;
    @Column(length = 100, nullable = false)
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 1. 새로운 유저를 "처음" 만들 때 쓰는 생성자
    public User(Long user_id, String email, String nickname, String password) {
        this.userId = user_id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void assignUserId(Long id) {
        this.userId = id;
    }
}
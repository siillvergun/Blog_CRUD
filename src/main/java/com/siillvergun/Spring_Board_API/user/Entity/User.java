package com.siillvergun.Spring_Board_API.user.Entity;

import com.siillvergun.Spring_Board_API.common.Base;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // 각 필드값을 가져오는 메서드 자동 생성
@Builder
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개변수로 가지는 생성자(엔티티 단에서는 지금은 이게 필요없음)
@Entity // 이 클래스를 Entity로 지정
@Table(name = "users") // sql예약어에 USER가 있어서 테이블명을 이렇게 지정해줘야한다. 그냥 USER는 사용 불가
public class User extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 원래 ++seq했던걸 이 어노테이션을 통해 자동으로 증가하게 만들 수 있음
    private Long userId;

    @Column(length = 40, nullable = false, unique = true)
    private String email;

    @Column(length = 15, nullable = false, unique = true)
    private String nickname;

    @Column(length = 100, nullable = false)
    private String password;

    public void updateProfile(String nickname, String email) {
        if (nickname != null && !nickname.isBlank()) {
            this.nickname = nickname;
        }
        if (email != null && !email.isBlank()) {
            this.email = email;
        }
    }
}
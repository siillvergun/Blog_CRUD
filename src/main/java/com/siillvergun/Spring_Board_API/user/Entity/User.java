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

    // 엔티티는 스스로의 상태를 보호해야하기 떄문에 이런 메서드들이 필요
    public void updateProfile(String nickname, String email) {
        if (nickname != null && !nickname.isBlank()) {
            this.nickname = nickname;
        }
        if (email != null && !email.isBlank()) {
            this.email = email;
        }
    }

    public void changePassword(String encryptedPassword) {
        // 보안 및 데이터 무결성을 위한 최소한의 검증
        if (encryptedPassword == null || encryptedPassword.isBlank()) {
            throw new IllegalArgumentException("암호화된 비밀번호가 유효하지 않습니다.");
        }

        this.password = encryptedPassword;
    }
}
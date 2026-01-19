package com.siillvergun.PostBoardTest.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 규칙: find + By + 필드명
    // 작동 원리: findByEmail(String email)이라고 쓰면,
    // 스프링은 내부적으로 SELECT * FROM user_entity WHERE email = ? 라는 SQL을 생성합니다.
    // 공통 기능(save, findAll 등): 이미 들어있으니 안 써도 됨.
    // 내 필드 검색(findByEmail 등): 인터페이스에 이름만 선언하면 됨 (구현은 스프링 몫).
    // 복잡한 기능: 직접 @Query를 써서 SQL을 알려줘야 함.

    Optional<User> findById(Long id); // ID로 유저 찾기

    Optional<User> findByNickname(String nickname);

}

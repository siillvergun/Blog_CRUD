package com.siillvergun.PostBoardTest.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API 사용하겟다는 어노테이션
@RequestMapping("/users") // 이 클래스 안의 메서드들이 공통으로 가질 URL 주소를 정합니다. (users를 가짐)
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 모든 유저 조회 API
    @GetMapping // 브라우저에 주소를 쳤을 때(조회 요청) 실행되는 메서드임을 나타냅니다.
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // @PathVariable : URL 경로에 들어있는 값(예: /users/1에서 1)을 변수로 가져올 때 사용합니다.
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }

    @PostMapping("/join")
    public User join(@RequestBody User user) {
        return userRepository.save(user);
    }
    // @RequsetBody: JSON 문자열을 자바 객체로 변환해주는 역할
    // 포스트맨에서 JSON 형식으로 보낸 문자열 데이터를 자바 객체로 변환
    // 내부적으로 Jackson이라는 라이브러리가 가동되어, JSON의 키(email)와 User 클래스의 필드(email)를 매칭
}

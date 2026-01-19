package com.siillvergun.Spring_Board_API.user.Controller;

import com.siillvergun.Spring_Board_API.user.Entity.UserJoinRequest;
import com.siillvergun.Spring_Board_API.user.Entity.UserProfileUpdateRequest;
import com.siillvergun.Spring_Board_API.user.Entity.UserResponse;
import com.siillvergun.Spring_Board_API.user.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API 사용하겟다는 어노테이션, API만 다루기 때문, 화면처리도 해줘야한다면 @Controller
@RequestMapping("/users") // 이 클래스 안의 메서드들이 공통으로 가질 URL 주소를 정합니다. (users를 가짐)
@RequiredArgsConstructor // final로 생성된 필드들을 매개변수로 가지는 생성자를 만들어줌
public class UserController {
    // UserService는 한 번 값을 할당 받으면 변경될 필요가 없기 때문에 final로 선언
    private final UserService userService; //

    /* UserController클래스의 생성자는 UserService를 매개변수로 받는데 이때 @RequiredArgsConstructor 얘를 쓰면
       UserService가 final이기 때문에 이 생성자를 자동으로 만들어줌
    public UserController(UserService userService) {
        this.userService = userService;
    }
    */


    // 모든 유저 조회 API
    @GetMapping // 브라우저에 주소를 쳤을 때(조회 요청) 실행되는 메서드임을 나타냅니다.
    public List<UserResponse> getAllUser() {
        return userService.getAllUsers();
    }

    // id로 조회
    @GetMapping("/{id}")
    // @PathVariable : URL 경로에 들어있는 값(예: /users/1에서 1)을 변수로 가져올 때 사용합니다.
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // 회원 가입
    @PostMapping("/join")
    public UserResponse joinUser(@RequestBody UserJoinRequest JoinRequest) {
        return userService.join(JoinRequest);
    }
    // @RequsetBody: JSON 문자열을 자바 객체로 변환해주는 역할
    // 포스트맨에서 JSON 형식으로 보낸 문자열 데이터를 자바 객체로 변환
    // 내부적으로 Jackson이라는 라이브러리가 가동되어, JSON의 키(email)와 User 클래스의 필드(email)를 매칭

    // 회원 정보 수정
    @PatchMapping("/{id}")
    public UserResponse updateProfile(@PathVariable Long id, @RequestBody UserProfileUpdateRequest UpdateRequest) {
        return userService.updateProfile(id, UpdateRequest);
    }

    // 회원 삭제
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
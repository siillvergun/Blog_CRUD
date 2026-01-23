package com.siillvergun.Spring_Board_API.user.controller;

import com.siillvergun.Spring_Board_API.user.dto.UserJoinRequest;
import com.siillvergun.Spring_Board_API.user.dto.UserPasswordUpdateRequest;
import com.siillvergun.Spring_Board_API.user.dto.UserProfileUpdateRequest;
import com.siillvergun.Spring_Board_API.user.dto.UserResponse;
import com.siillvergun.Spring_Board_API.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // REST API 사용하겟다는 어노테이션, API만 다루기 때문, 화면처리도 해줘야한다면 @Controller
@RequestMapping("/users") // 이 클래스 안의 메서드들이 공통으로 가질 URL 주소를 정합니다. (users를 가짐)
@RequiredArgsConstructor // final로 생성된 필드들을 매개변수로 가지는 생성자를 만들어줌
public class UserController {
    // UserService는 한 번 값을 할당 받으면 변경될 필요가 없기 때문에 final로 선언
    private final UserService userService; //

    /// 모든 유저 조회 API
    @GetMapping // 브라우저에 주소를 쳤을 때(조회 요청) 실행되는 메서드임을 나타냅니다.
    public ResponseEntity<List<UserResponse>> getAllUser() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // 200ok를 넘겨줌
    }

    
    @GetMapping("/{id}")
    // @PathVariable : URL 경로에 들어있는 값(예: /users/1에서 1)을 변수로 가져올 때 사용합니다.
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse response = userService.getUserResponse(id);
        return ResponseEntity.ok(response);
    }

    /// 회원 가입
    @PostMapping("/join")
    public ResponseEntity<UserResponse> joinUser(@RequestBody UserJoinRequest JoinRequest) {
        UserResponse response = userService.join(JoinRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    // @RequsetBody: JSON 문자열을 자바 객체로 변환해주는 역할
    // 포스트맨에서 JSON 형식으로 보낸 문자열 데이터를 자바 객체로 변환
    // 내부적으로 Jackson이라는 라이브러리가 가동되어, JSON의 키(email)와 User 클래스의 필드(email)를 매칭

    /// 회원 정보 수정
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateProfile(@PathVariable Long id, @RequestBody UserProfileUpdateRequest updateRequest) {
        UserResponse response = userService.updateProfile(id, updateRequest);
        return ResponseEntity.ok(response);
    }

    /// 패스워드 수정(패스워드는 사용자에게 넘겨주지 않음)
    @PatchMapping("/password/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @RequestBody UserPasswordUpdateRequest updateRequest) {
        userService.updatePassword(id, updateRequest);
        return ResponseEntity.noContent().build();
    }

    /// 회원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
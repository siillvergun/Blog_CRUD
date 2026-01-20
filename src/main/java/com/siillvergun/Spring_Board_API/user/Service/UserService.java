package com.siillvergun.Spring_Board_API.user.Service;

import com.siillvergun.Spring_Board_API.user.Entity.*;
import com.siillvergun.Spring_Board_API.user.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// 비즈니스 로직을 담는 곳
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Create(Request DTO)
    public UserResponse join(UserJoinRequest joinRequest) {
        User user = joinRequest.toEntity();
        User savedUser = userRepository.save(user);
        return UserResponse.from(savedUser); // 이제 포스트맨 응답에 비밀번호가 사라집니다!
    }

    // Read(조회)
    // 전체 조회
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream(). // stream을 사용해 각 엔티티를 UserResponse DTO로 변환
                map(UserResponse::from). // User를 UserResponse로 변환
                toList(); // 결과를 리스트로 모음
    }

    // 단건 조회
    public UserResponse getUserById(Long id) {
        return userRepository.findById(id).map(UserResponse::from).
                orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }

    // 메서드 내에서 서버를 위한 유저 검색 메서드
    public User getUserByIdForServer(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }

    // Update(갱신, DTO)
    @Transactional // 변경을 감지하는 어노테이션
    public UserResponse updateProfile(Long id, UserProfileUpdateRequest updateRequest) {
        // 1. DB에서 기존 유저를 조회
        User user = getUserByIdForServer(id);

        // 2. 도메인 메서드를 통해 필드를 수정 (Dirty Checking 발생)
        // update에서는 엔티티로 변환 안해도 됨. join같은 경우는 새로운 객체를 만들기 때문에 엔티티로 변환하지만
        // update는 이미 JPA가 해당 객체를 관리하고 있으므로 그냥 메서드를 통해 수정이 필요한 필드만 수정하면 된다.
        user.updateProfile(updateRequest.getNickname(), updateRequest.getEmail());

        // 3. 보안을 위해 엔티티 대신 응답 전용 DTO로 변환하여 반환
        return UserResponse.from(user);
    }

    @Transactional
    public void updatePassword(Long id, UserPasswordUpdateRequest updateRequest) {
        User user = getUserByIdForServer(id);

        // 1. 현재 비밀번호가 일치하는지 확인
        // passwordEncoder.matches(평문, 암호문) 메서드를 사용합니다.
        if (!passwordEncoder.matches(updateRequest.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 2. 새 비밀번호를 암호화하여 저장
        String encryptedPassword = passwordEncoder.encode(updateRequest.getNewPassword());
        user.changePassword(encryptedPassword);
        System.out.println("Password Changed!!");
    }


    // Delete(삭제)
    public void deleteUser(Long id) {
        // 1. 해당 유저가 있는지 확인 (선택 사항이나 권장됨)
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("삭제하려는 유저가 존재하지 않습니다.");
        }
        // 2. 삭제 실행
        System.out.println("Delete!!");
        userRepository.deleteById(id);
    }
}
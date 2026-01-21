package com.siillvergun.Spring_Board_API.user.service;

import com.siillvergun.Spring_Board_API.user.DTO.UserJoinRequest;
import com.siillvergun.Spring_Board_API.user.DTO.UserPasswordUpdateRequest;
import com.siillvergun.Spring_Board_API.user.DTO.UserProfileUpdateRequest;
import com.siillvergun.Spring_Board_API.user.DTO.UserResponse;
import com.siillvergun.Spring_Board_API.user.entity.User;
import com.siillvergun.Spring_Board_API.user.repository.UserRepository;
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
        // 평문 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(joinRequest.getPassword());

        // 암호화된 패스워드를 DTO에서 엔티티로 변환하는 메서드의 매개변수로 넘겨줌
        User user = joinRequest.toEntity(encodedPassword);
        return UserResponse.toDTO(userRepository.save(user)); // 이제 포스트맨 응답에 비밀번호가 사라짐
    }

    // Read(조회)
    // 전체 조회
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream(). // 컬렉션을 스트림으로 변환 (스트림이란 데이터 소스를 추상화하여 무슨 데이터인지 상관하지않고 같은 방법으로 처리가능 )
                map(UserResponse::toDTO). // 각 User 객체를 UserResponse로 변환, [ stream().map(클래스명::메서드명) ]
                toList(); // 그 결과를 리스트로 모음
    }

    // 단건 조회
    // DTO는 외부와 데이터를 주고 받을 때에만 사용
    public UserResponse getUserResponse(Long id) {
        return userRepository.findById(id).map(UserResponse::toDTO).
                orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }

    // 백엔드 내부에서는 엔티티를 가지고 데이터를 관리하는게 좋기 때문에 메서드 분리
    // JPA는 Entity를 관리하기 때문에 이 객체의 값이 바뀔 때 트랜젝션이 끝날 때 JPA가 자동으로 DB에 반영해줌
    // 메서드 내에서 서버를 위한 유저 검색 메서드
    public User findUserById(Long id) {
        return userRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
    }

    // Update(갱신, DTO)
    @Transactional // 값 변경을 감지하는 어노테이션
    public UserResponse updateProfile(Long id, UserProfileUpdateRequest updateRequest) {
        // 1. DB에서 기존 유저를 조회
        User user = findUserById(id);

        // 2. 도메인 메서드를 통해 필드를 수정 (Dirty Checking 발생)
        // update에서는 엔티티로 변환 안해도 됨. join같은 경우는 새로운 객체를 만들기 때문에 엔티티로 변환하지만
        // update는 이미 JPA가 해당 객체를 관리하고 있으므로 그냥 메서드를 통해 수정이 필요한 필드만 수정하면 된다.
        // @Transactional이 값의 변경을 감지해 처리
        user.changeProfile(updateRequest.getNickname(), updateRequest.getEmail());


        // 3. 보안을 위해 엔티티 대신 응답 전용 DTO로 변환하여 반환
        return UserResponse.toDTO(user);
    }

    @Transactional
    public void updatePassword(Long id, UserPasswordUpdateRequest updateRequest) {
        User user = findUserById(id);

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
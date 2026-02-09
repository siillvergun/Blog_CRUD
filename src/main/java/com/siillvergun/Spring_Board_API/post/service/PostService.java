package com.siillvergun.Spring_Board_API.post.service;

import com.siillvergun.Spring_Board_API.global.CustomException;
import com.siillvergun.Spring_Board_API.global.ErrorCode;
import com.siillvergun.Spring_Board_API.post.dto.PostRequestDto;
import com.siillvergun.Spring_Board_API.post.dto.PostResponseDto;
import com.siillvergun.Spring_Board_API.post.entity.Post;
import com.siillvergun.Spring_Board_API.post.repository.PostRepository;
import com.siillvergun.Spring_Board_API.user.entity.User;
import com.siillvergun.Spring_Board_API.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.siillvergun.Spring_Board_API.global.ErrorCode.POST_NOT_FOUND;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;


    /// create
    @Transactional // 쓰기 작업이 있는 메서드만
    public PostResponseDto createPost(PostRequestDto postRequestDto, Long userId) {
        User author = userService.findUserById(userId);

        Post post = postRequestDto.toEntity(author);
        return PostResponseDto.from(postRepository.save(post));
    }


    /// read
    // 게시글 전체 조회
    public List<PostResponseDto> getAllPost() {
        List<Post> posts = postRepository.findAll();

        return posts.stream(). // 컬렉션을 스트림으로 변환 (스트림이란 데이터 소스를 추상화하여 무슨 데이터인지 상관하지않고 같은 방법으로 처리가능 )
                map(PostResponseDto::from). // 각 User 객체를 UserResponse로 변환, [ stream().map(클래스명::메서드명) ]
                toList(); // 그 결과를 리스트로 모음
    }

    // 작성자로 조회
    public List<PostResponseDto> getPostByAuthor(Long userId) {
        List<Post> postsByUserId = postRepository.findByAuthor_UserId(userId);

        return postsByUserId.stream()
                .map(PostResponseDto::from)
                .toList();
    }

    // 제목으로 조회
    public List<PostResponseDto> getPostByTitle(String title) {
        List<Post> postsByTitle = postRepository.findByTitleContaining(title);

        return postsByTitle.stream()
                .map(PostResponseDto::from)
                .toList();
    }

    // 내용으로 조회
    public List<PostResponseDto> getPostByContent(String keyword) {
        List<Post> postsByContent = postRepository.findByContentContaining(keyword);

        return postsByContent.stream()
                .map(PostResponseDto::from)
                .toList();
    }

    // postRepository.findById()은 반환값이 Optional, 또한 내부 로직에서는 DTO보다 Entity를 직접 다루는게 좋음
    //
    public Post findByPostId(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
    }

    /// update
    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto updateDto, Long userId) {
        Post post = findByPostId(postId);

        // 작성자 검증 로직 (예시)
        if (!post.getAuthor().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        post.updateTitle(updateDto.getTitle());
        post.updateContent(updateDto.getContent());
        return PostResponseDto.from(post);
    }


    /// delete
    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = findByPostId(postId);

        // 작성자 본인인지 확인 로직 추가
        if (!post.getAuthor().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        postRepository.deleteById(postId);
        log.warn("게시글 삭제 실행 - ID: {}", postId);
    }
}

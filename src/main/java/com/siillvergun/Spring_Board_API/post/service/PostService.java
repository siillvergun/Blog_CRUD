package com.siillvergun.Spring_Board_API.post.service;

import com.siillvergun.Spring_Board_API.post.dto.PostRequestDto;
import com.siillvergun.Spring_Board_API.post.dto.PostResponseDto;
import com.siillvergun.Spring_Board_API.post.entity.Post;
import com.siillvergun.Spring_Board_API.post.repository.PostRepository;
import com.siillvergun.Spring_Board_API.user.entity.User;
import com.siillvergun.Spring_Board_API.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional // 쓰기 작업이 있는 메서드만
    public PostResponseDto createPost(PostRequestDto postRequestDto, Long userId) {
        User author = userService.findUserById(userId);

        Post post = postRequestDto.toEntity(author);
        return PostResponseDto.from(postRepository.save(post));
    }

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


    //@Transactional
    //public PostResponseDto updateTitle(String title)
    //@Transactional
    //public PostResponseDto updateContent(String content)

}

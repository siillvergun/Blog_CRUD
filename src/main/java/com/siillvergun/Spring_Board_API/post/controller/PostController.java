package com.siillvergun.Spring_Board_API.post.controller;

import com.siillvergun.Spring_Board_API.post.dto.PostRequestDto;
import com.siillvergun.Spring_Board_API.post.dto.PostResponseDto;
import com.siillvergun.Spring_Board_API.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    ///  게시글 생성
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody PostRequestDto postRequestDto,
            @RequestParam Long userId

    ) {
        // DTO 내부의 userId를 사용하여 생성
        PostResponseDto response = postService.createPost(postRequestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /// 모든 게시글 검색 & 제목/내용 검색
    // **"전체 조회를 하되, 만약 제목(title)또는 내용(content)이라는 파라미터가 들어오면 검색을 하겠다"**는 논리로 접근
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content
    ) {
        // 제목 검색
        if (title != null && !title.isBlank()) {
            List<PostResponseDto> searchTitle = postService.getPostByTitle(title);
            return ResponseEntity.ok(searchTitle);
        }

        // 내용 검색
        else if (content != null && !content.isBlank()) {
            List<PostResponseDto> searchContent = postService.getPostByContent(content);
            return ResponseEntity.ok(searchContent);
        }

        // 전체 검색
        List<PostResponseDto> AllPosts = postService.getAllPost();
        return ResponseEntity.ok(AllPosts);
    }


    /// 유저 게시글 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDto>> getUserPosts(@PathVariable Long userId) {
        // 경로 변수로 받은 userId를 서비스에 전달
        List<PostResponseDto> userPosts = postService.getPostByAuthor(userId);
        return ResponseEntity.ok(userPosts);
    }


    /// 게시글 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @RequestBody PostRequestDto updateDto,
            @RequestParam Long userId
    ) {
        PostResponseDto response = postService.updatePost(postId, updateDto, userId);
        return ResponseEntity.ok(response);
    }


    /// 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam Long userId // DELETE는 바디 대신 파라미터 사용 권장
    ) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }
}

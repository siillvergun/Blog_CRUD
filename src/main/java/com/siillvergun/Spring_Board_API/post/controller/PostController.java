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
@RequiredArgsConstructor // final
public class PostController {
    private final PostService postService;

    ///  게시글 생성
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @RequestBody PostRequestDto postRequestDto,
            @PathVariable Long userId

    ) {
        // DTO 내부의 userId를 사용하여 생성
        PostResponseDto response = postService.createPost(postRequestDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /// 모든 게시글 검색
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPosts(
    ) {
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
            @RequestParam Long userId,
            @RequestBody PostRequestDto updateDto) {
        PostResponseDto response = postService.updatePost(postId, updateDto, userId);
        return ResponseEntity.ok(response);
    }


    /// 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        postService.deletePost(postId, userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> togglePostLike(
            @PathVariable Long postId,
            @RequestParam Long userId
    ) {
        postService.toggleLike(postId, userId);
        return ResponseEntity.ok().build();
    }
}

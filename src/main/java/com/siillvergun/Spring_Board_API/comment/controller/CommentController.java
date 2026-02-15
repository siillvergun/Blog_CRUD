package com.siillvergun.Spring_Board_API.comment.controller;

import com.siillvergun.Spring_Board_API.comment.dto.CommentRequestDto;
import com.siillvergun.Spring_Board_API.comment.dto.CommentResponseDto;
import com.siillvergun.Spring_Board_API.comment.service.CommentService;
import com.siillvergun.Spring_Board_API.post.service.PostService;
import com.siillvergun.Spring_Board_API.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody CommentRequestDto commentRequestDto,
            @RequestParam Long userId,
            @RequestParam Long postId
    ) {
        CommentResponseDto response = commentService.createComment(commentRequestDto, userId, postId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> getAllComment(
    ) {
        List<CommentResponseDto> response = commentService.getAllComment();
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestParam String changedContent
    ) {
        CommentResponseDto response = commentService.updateComment(commentId, changedContent);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

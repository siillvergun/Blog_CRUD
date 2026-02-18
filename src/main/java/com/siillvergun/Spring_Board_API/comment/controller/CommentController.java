package com.siillvergun.Spring_Board_API.comment.controller;

import com.siillvergun.Spring_Board_API.comment.dto.CommentRequestDto;
import com.siillvergun.Spring_Board_API.comment.dto.CommentResponseDto;
import com.siillvergun.Spring_Board_API.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody CommentRequestDto commentRequestDto,
            @PathVariable Long userId,
            @PathVariable Long postId
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

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        CommentResponseDto response = commentService.updateComment(commentId, commentRequestDto.getContent());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{commentId}/like")
    public ResponseEntity<Void> toggleCommentLike(
            @PathVariable Long commentId,
            @RequestParam Long userId
    ) {
        commentService.toggleLike(userId, commentId);
        return ResponseEntity.ok().build();
    }
}

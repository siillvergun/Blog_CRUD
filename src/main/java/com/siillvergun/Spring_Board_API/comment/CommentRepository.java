package com.siillvergun.Spring_Board_API.comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment); // 유저 저장

    Optional<Comment> findById(Long id); // ID로 유저 찾기

    List<Comment> findByAuthorId(Long userId);

    List<Comment> findByPostId(Long postId);

    List<Comment> findAll(); // 모든 유저 목록 보
}

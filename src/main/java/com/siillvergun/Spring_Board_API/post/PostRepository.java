package com.siillvergun.Spring_Board_API.post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post); // 유저 저장

    Optional<Post> findById(Long id); // ID로 유저 찾기

    List<Post> findByAuthorId(Long userId);

    List<Post> findAll(); // 모든 유저 목록 보기
}

package com.siillvergun.Spring_Board_API.post.repository;

import com.siillvergun.Spring_Board_API.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // userId로 작성자 찾기
    List<Post> findByAuthor_UserId(Long userId);

    // 제목으로 검색
    List<Post> findByTitleContaining(String title);

    // 내용으로 검색
    List<Post> findByContentContaining(String keyword);
}

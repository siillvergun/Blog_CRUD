package com.siillvergun.Spring_Board_API.post.repository;

import com.siillvergun.Spring_Board_API.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // userId로 작성자 찾기
    List<Post> findByAuthorUserId(Long userId);

    // 제목으로 검색
    List<Post> findByTitleContaining(String title);

    // 내용으로 검색
    List<Post> findByContentContaining(String keyword);

    // fetch join - N+1문제 방지
    @Query("select p from Post p join fetch p.author")
    List<Post> findAllWithAuthor();
}

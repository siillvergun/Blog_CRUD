package com.siillvergun.Spring_Board_API.post.repository;

import com.siillvergun.Spring_Board_API.post.entity.Post;
import com.siillvergun.Spring_Board_API.post.entity.PostLike;
import com.siillvergun.Spring_Board_API.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    // 1. 내가 눌렀는지 확인 (Boolean)
    Optional<PostLike> findByUserAndPost(User user, Post post);

    // 특정 게시의 모든 좋아요 삭제
    void deleteByPost(Post post);
}

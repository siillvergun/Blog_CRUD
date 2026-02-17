package com.siillvergun.Spring_Board_API.post.repository;

import com.siillvergun.Spring_Board_API.post.entity.Post;
import com.siillvergun.Spring_Board_API.post.entity.PostLike;
import com.siillvergun.Spring_Board_API.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    // 1. 내가 눌렀는지 확인 (Boolean)
    boolean existsByUserAndPost(User user, Post post);
}

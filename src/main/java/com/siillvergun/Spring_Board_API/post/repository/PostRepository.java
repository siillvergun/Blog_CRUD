package com.siillvergun.Spring_Board_API.post.repository;

import com.siillvergun.Spring_Board_API.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}

package com.siillvergun.Spring_Board_API.comment.repository;

import com.siillvergun.Spring_Board_API.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.author join fetch c.post")
    List<Comment> findAllWithAuthorAndPost();
}

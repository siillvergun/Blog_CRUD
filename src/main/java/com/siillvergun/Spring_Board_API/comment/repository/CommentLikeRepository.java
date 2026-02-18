package com.siillvergun.Spring_Board_API.comment.repository;

import com.siillvergun.Spring_Board_API.comment.entity.Comment;
import com.siillvergun.Spring_Board_API.comment.entity.CommentLike;
import com.siillvergun.Spring_Board_API.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);

    void deleteByComment(Comment comment);
}

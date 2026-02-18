package com.siillvergun.Spring_Board_API.comment.service;

import com.siillvergun.Spring_Board_API.comment.dto.CommentRequestDto;
import com.siillvergun.Spring_Board_API.comment.dto.CommentResponseDto;
import com.siillvergun.Spring_Board_API.comment.entity.Comment;
import com.siillvergun.Spring_Board_API.comment.entity.CommentLike;
import com.siillvergun.Spring_Board_API.comment.repository.CommentLikeRepository;
import com.siillvergun.Spring_Board_API.comment.repository.CommentRepository;
import com.siillvergun.Spring_Board_API.global.CustomException;
import com.siillvergun.Spring_Board_API.post.entity.Post;
import com.siillvergun.Spring_Board_API.post.service.PostService;
import com.siillvergun.Spring_Board_API.user.entity.User;
import com.siillvergun.Spring_Board_API.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.siillvergun.Spring_Board_API.global.ErrorCode.POST_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommentService {
    private final UserService userService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    // Create
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long userId, Long postId) {
        // 누가, 어디에 쓰는지가 중요하므로 id를 통해 DB에서 객체를 찾아온다
        User user = userService.findUserById(userId);
        Post post = postService.findByPostId(postId);

        // 클라이언트가 보낸 요청을 엔티티로 바꿔 dto에서 entity로 바꾼다.
        Comment comment = commentRequestDto.toEntity(user, post);

        // 완성된 entity를 DB에 저장함. 이떄 DB에서 엔티티에 id, 생성일 등 서버에서 처리해주는 값들을 채워넣는다.
        // 그런뒤 완성된 entity를 from메서드를 통해 클라이언트에 넘겨줄 값만 선정해 응답Dto로 넘겨준다.
        return CommentResponseDto.from(commentRepository.save(comment));
    }


    // Read
    public List<CommentResponseDto> getAllComment() {
        List<Comment> comments = commentRepository.findAllWithAuthorAndPost();

        return comments.stream()
                .map(CommentResponseDto::from)
                .toList();
    }

    private Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
    }

    // Update
    @Transactional
    public CommentResponseDto updateComment(Long commentId, String changedString) {
        Comment comment = findByCommentId(commentId);

        comment.changeComment(changedString);
        return CommentResponseDto.from(comment);
    }


    // Delete
    @Transactional
    public void deleteComment(Long commendId) {
        Comment comment = findByCommentId(commendId);
        commentRepository.delete(comment);
        log.warn("댓글 삭제 실행 - ID: {}", commendId);
    }

    @Transactional
    public void toggleLike(Long userId, Long postId) {
        // 1. 게시글과 유저 존재 확인
        Post post = postService.findByPostId(postId);
        User user = userService.findUserById(userId);

        // 2. 이미 좋아요를 눌렀는지 확인
        Optional<CommentLike> optionalLike = commentLikeRepository.findByUserAndPost(user, post);

        if (optionalLike.isPresent()) {
            // [CASE 1] 이미 눌렀다면 -> 좋아요 취소
            commentLikeRepository.delete(optionalLike.get()); // 1. like 테이블에서 삭제
            post.decreaseLikeCount(); // 2. post 테이블의 카운트 -1 (Dirty Checking)
            log.info("like");
        } else {
            // [CASE 2] 안 눌렀다면 -> 좋아요 등록
            commentLikeRepository.save(new CommentLike(user, post)); // 1. like 테이블에 저장
            post.increaseLikeCount(); // 2. post 테이블의 카운트 +1 (Dirty Checking)
            log.info("dislike");
        }
    }
}

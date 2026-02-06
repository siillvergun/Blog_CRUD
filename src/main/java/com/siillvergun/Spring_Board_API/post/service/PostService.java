package com.siillvergun.Spring_Board_API.post.service;

import com.siillvergun.Spring_Board_API.post.dto.PostResponseDto;
import com.siillvergun.Spring_Board_API.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostResponseDto createPost(Long userId) {

    }
}

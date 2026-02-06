package com.siillvergun.Spring_Board_API.post.controller;

import com.siillvergun.Spring_Board_API.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("post")
@RequiredArgsConstructor
public class PostController {
    private final UserService userService;


}

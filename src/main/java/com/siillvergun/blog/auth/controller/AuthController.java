package com.siillvergun.blog.auth.controller;

import com.siillvergun.blog.auth.jwt.JwtFilter;
import com.siillvergun.blog.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtFilter jwtFilter;

    
}

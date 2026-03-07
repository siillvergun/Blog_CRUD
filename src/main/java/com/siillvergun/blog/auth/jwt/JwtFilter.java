package com.siillvergun.blog.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component // 스프링 빈으로 등록
@RequiredArgsConstructor
// OncePerRequestFilter는 요청당 한 번만 실행되도록 보장해주는 필터 베이스 클래스
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // 요청 헤더에서 Authorization 값을 꺼냄
        String authHeader = httpServletRequest.getHeader("Authorization");
        // Bearer를 땐 순수 JWT 문자열
        String token = jwtTokenProvider.BearerParse(authHeader);

        if (token != null) {
            try {
                // userId를 JWT문자열에서 파싱
                Long userId = jwtTokenProvider.parseToken(token);
                // 스프링 시큐리티용 인증 객체 생성
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userId,
                        null,
                        Collections.emptyList()
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

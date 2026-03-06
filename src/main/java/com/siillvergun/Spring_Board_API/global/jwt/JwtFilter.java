package com.siillvergun.Spring_Board_API.global.jwt;

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
import java.util.ArrayList;

// @Bean이랑 의미는 같지만 Bean은 외부 라이브러리의 클래스에, @Component는 내가 만든 클래스에 붙이는 것
// @Service, @Controller같은 어노테이션들도 내부적으로 @Component가 있어서 스프링
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    // 모든 API 요청은 컨트롤러에 가기 전에 무조건 이 메서드를 거치게 됩니다! (입국 심사대)
    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 프론트엔드가 보낸 요청의 헤더(Header)에서 "Authorization"이라는 이름의 티켓을 꺼냅니다.
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        // 2. 티켓이 아예 없거나, "Bearer "로 시작하지 않으면 짭(가짜)이거나 로그인을 안 한 겁니다.
        // 그러면 그냥 "통과~" 시킵니다. (나중에 4단계에서 시큐리티가 "너 로그인 안했네?" 하고 알아서 쫓아냅니다)
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 3. 정상적인 티켓이라면, 앞에 붙은 "Bearer " 글자를 떼어내고 진짜 토큰(aaaa.bbbb.cccc)만 남깁니다.
        String token = authorizationHeader.substring(7);

        // 4. 우리가 아까 만든 조폐공사(JwtUtil)를 불러서 토큰이 진짜인지 검사합니다!
        if (jwtUtil.validateToken(token)) {
            // 5. 진짜 토큰이라면, 토큰 안에서 유저 이메일을 쏙 뽑아옵니다.
            String email = jwtUtil.getEmailFromToken(token);

            // 6. 스프링 시큐리티에게 "어! 이 사람 신분 확인 끝났어! 통과시켜!" 라고 임시 출입증을 만들어 줍니다.
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());

            // 7. 발급한 임시 출입증을 스프링 시큐리티의 '안전 금고(SecurityContext)'에 보관합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 8. 검사가 끝났으니, 다음 목적지(컨트롤러)로 가던 길을 가게 해줍니다.
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
package com.siillvergun.blog.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// SecurityConfig에서 선언한 BCryptPasswordEncoder는 외부 라이브러리에 있는 클래스. 따라서 내 클래스로 가져와 객체로 만들어둠.
// 이때, 외부 라이브러리에 대한 어노테이션을 직접 붙이지 못하니까, @Bean을 통해 이 메서드가 실행돼서 나오는 이 객체를 스프링이 관리하게 만든다.

@Configuration // 이 클래스는 설정 파일임을 스프링에게 알려줌. 이 안에 정의된 메서드(@Bean)들을 스프링이 읽어서 관리하게 됨.
@EnableWebSecurity // 이제부터 스프링 시큐리티의 웹 보안 기능을 활성화하는 스위치
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean // 외부에서 가져온 객체를 스프링이 관리하게 시키는 어노테이션
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // REST API 서버는 보통 세션을 쓰지 않고 토큰(JWT 등)을 쓰기 때문에, 테스트 편의를 위해 CSRF(사이트 간 요청 위조) 방어 기능을 잠시 꺼두는 것
        http.csrf(csrf -> csrf.disable())
                // 아래 한 줄을 추가하여 H2 콘솔의 프레임 구조를 허용합니다.
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));

        // Jwt는 stateless 상태
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                // 일단 모든 api요청 인증없이 가능하게
                .anyRequest().permitAll()
        );

        return http.build();
    }
}
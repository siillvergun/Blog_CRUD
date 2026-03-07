package com.siillvergun.blog.common.security;

import com.siillvergun.blog.common.error.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // 자바 객체를 JSON으로 예쁘게 바꿔주는 마법의 도구입니다.
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authException)
            throws IOException, ServletException {

        // 1. 개발자님이 만드신 ErrorCode에서 NO_LONGIN_REQUEST를 쏙 뽑아옵니다.
        ErrorCode errorCode = ErrorCode.NO_LONGIN_REQUEST;

        // 2. 기본 응답 설정 (JSON, 한글 깨짐 방지)
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");

        // 3. ⭐️ ErrorCode에 있는 HttpStatus(UNAUTHORIZED)의 숫자값(401)을 꺼내서 세팅!
        httpServletResponse.setStatus(errorCode.getHttpStatus().value());

        // 4. JSON에 담을 바구니를 만들고, ErrorCode의 내용(401, 인증되지 않은 요청입니다)을 넣습니다.
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", errorCode.getCode());
        errorResponse.put("message", errorCode.getMessage());

        // 5. 바구니를 JSON 문자열로 변환 후 프론트엔드로 전송!
        String result = objectMapper.writeValueAsString(errorResponse);
        httpServletResponse.getWriter().write(result);
    }
}
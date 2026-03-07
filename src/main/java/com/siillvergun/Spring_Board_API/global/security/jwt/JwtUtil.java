package com.siillvergun.Spring_Board_API.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component // @Bean과 비슷한 역할
public class JwtUtil {

    private final Key key;
    private final long expiration;

    // application.properties에 적어둔 비밀키와 만료시간을 스프링이 쏙쏙 뽑아서 넣어줍니다.
    public JwtUtil(@Value("${jwt.secret}") String secretKey,
                   @Value("${jwt.expiration}") long expiration) {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes()); // application.properties에 정의해둔 무작위 문자열을 키형태로 변환
        this.expiration = expiration; // 마찬가지로 application.properties에 정의해둔 유효기간을 가져옴
    }

    // 유저가 로그인을 성공하면 이 메서드를 불러서 토큰을 만들어 줍니다.
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(email) // 토큰 주인의 이름표 (여기서는 유저의 이메일)
                .setIssuedAt(now) // 발급 시간
                .setExpiration(expiryDate) // 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 우리 서버의 비밀키로 위조 방지
                .compact(); // JWT라는 긴 문자열로 압축!
    }

    //
    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // 반드시 발급할 때 썼던 비밀키를 넣어야만 열립니다. 해커는 못 엽니다!
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // 아까 넣었던 email을 꺼내줍니다.
    }

    // 3️⃣ 토큰 위조/만료 검사기
    // 프론트엔드가 보낸 토큰이 유효한지 확인합니다.
    public boolean validateToken(String token) {
        try {
            // 이 코드가 에러 없이 무사히 지나가면 정상적인 토큰입니다.
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // 기간이 지났거나, 해커가 글자를 하나라도 바꿨거나, 이상한 토큰이면 무조건 에러가 터져서 여기로 옵니다.
            return false;
        }
    }
}
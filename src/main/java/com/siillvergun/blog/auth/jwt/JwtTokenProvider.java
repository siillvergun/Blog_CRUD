package com.siillvergun.blog.auth.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtEncoder jwtEncoder;
    private final long expiration;

    public String createAccessToken(Long userId, String email) {
        // 토큰 발급 시각
        Instant now = Instant.now();

        // 토큰 안에 들어가는 내용
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(String.valueOf(userId))
                .claim("email", email)
                .issuedAt(now)
                .expiresAt(now.plusMillis(expiration))
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(jwsHeader, jwtClaimsSet)
        ).getTokenValue();
    }
}

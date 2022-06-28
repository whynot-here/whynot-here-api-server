package handong.whynot.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Component
public class SignInTokenGenerator {

    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Value("${jwt.expiredTime.access-token}")
    private Long accessExpiredTime;

    @Value("${jwt.expiredTime.access-token}")
    private Long refreshExpiredTime;

    public String accessToken(Long userId) {
        return signInToken(userId, LocalDateTime.now().plusMinutes(accessExpiredTime));
    }

    public String refreshToken(Long userId) {
        return signInToken(userId, LocalDateTime.now().plusMinutes(refreshExpiredTime));
    }

    private String signInToken(Long userId, LocalDateTime expirationTime) {
        return signInToken(String.valueOf(userId), Date.from(expirationTime.toInstant(ZoneOffset.UTC)));
    }

    @SneakyThrows
    private String signInToken(String userId, Date expirationTime) {
        // TODO: 토큰 검증 들어갈때 처리하기
        JWSSigner jwsSigner = new MACSigner(jwtSecret);
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = claimsSetForSignInToken(userId, expirationTime);

        SignedJWT signedJWT = new SignedJWT(
                jwsHeader,
                jwtClaimsSet
        );

        signedJWT.sign(jwsSigner);
        return signedJWT.serialize();
    }

    private JWTClaimsSet claimsSetForSignInToken(String userId, Date expirationTime) {
        return new JWTClaimsSet.Builder()
                .subject(userId)
                .expirationTime(expirationTime)
                .build();
    }
}


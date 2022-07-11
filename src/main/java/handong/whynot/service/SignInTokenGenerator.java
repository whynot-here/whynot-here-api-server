package handong.whynot.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.exception.account.AccountNotValidToken;
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

    @Value("${jwt.expiredTime.refresh-token}")
    private Long refreshExpiredTime;

    public String accessToken(Long userId) {
        return signInToken(userId, LocalDateTime.now().plusMinutes(accessExpiredTime), "access");
    }

    public String refreshToken(Long userId) {
        return signInToken(userId, LocalDateTime.now().plusMinutes(refreshExpiredTime), "refresh");
    }

    private String signInToken(Long userId, LocalDateTime expirationTime, String audience) {
        return signInToken(String.valueOf(userId), Date.from(expirationTime.toInstant(ZoneOffset.UTC)), audience);
    }

    private String signInToken(String userId, Date expirationTime, String audience) {
        try {
            JWSSigner jwsSigner = new MACSigner(jwtSecret);
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

            JWTClaimsSet jwtClaimsSet = claimsSetForSignInToken(userId, expirationTime, audience);


            SignedJWT signedJWT = new SignedJWT(
                    jwsHeader,
                    jwtClaimsSet
            );

            signedJWT.sign(jwsSigner);
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new AccountNotValidToken(AccountResponseCode.ACCOUNT_NOT_VALID_TOKEN_SECRET);
        }
    }

    private JWTClaimsSet claimsSetForSignInToken(String userId, Date expirationTime, String audience) {
        return new JWTClaimsSet.Builder()
                .subject(userId)                  // 사용자
                .expirationTime(expirationTime)   // 만료시간
                .audience(audience)               // 의도된 수신자
                .build();
    }
}


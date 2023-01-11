package handong.whynot.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import handong.whynot.domain.Account;
import handong.whynot.domain.Role;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.exception.account.AccountNotValidToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class SignInTokenGenerator {

    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Value("${jwt.expiredTime.access-token}")
    private Long accessExpiredTime;

    @Value("${jwt.expiredTime.refresh-token}")
    private Long refreshExpiredTime;

    public String accessToken(Account account) {
        return signInToken(account, LocalDateTime.now().plusMinutes(accessExpiredTime), "access");
    }

    public String refreshToken(Account account) {
        return signInToken(account, LocalDateTime.now().plusMinutes(refreshExpiredTime), "refresh");
    }

    private String signInToken(Account account, LocalDateTime expirationTime, String audience) {
        return signInToken(account, Date.from(expirationTime.toInstant(ZoneOffset.UTC)), audience);
    }

    public String signInToken(Account account, Date expirationTime, String audience) {
        try {
            JWSSigner jwsSigner = new MACSigner(jwtSecret);
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

            JWTClaimsSet jwtClaimsSet = claimsSetForSignInToken(account, expirationTime, audience);

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

    private JWTClaimsSet claimsSetForSignInToken(Account account, Date expirationTime, String audience) {
        return new JWTClaimsSet.Builder()
                .subject(String.valueOf(account.getId()))                  // 사용자
                .expirationTime(expirationTime)   // 만료시간
                .audience(audience)               // 의도된 수신자
                .claim("roles", account.getRoles().stream().map(Role::getCode).collect(Collectors.toList()))
                .build();
    }
}


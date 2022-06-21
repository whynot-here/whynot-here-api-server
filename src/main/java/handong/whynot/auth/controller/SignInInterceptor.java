package handong.whynot.auth.controller;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jose.crypto.MACVerifier;
import handong.whynot.domain.Account;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.exception.account.AccountNotVerifiedException;
import handong.whynot.exception.account.AccountTokenExpiredException;
import handong.whynot.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class SignInInterceptor implements HandlerInterceptor {

    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Value("${jwt.expiredTime.access-token}")
    private Long accessExpiredTime;

    private static final List<String> PATH_TO_INCLUDE = List.of(
        "/v2/need/**"
    );

    private static final List<String> PATH_TO_EXCLUDE = List.of(
    );

    private final AccountRepository accountRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");

        // 토큰이 없는 경우 (Forbidden Exception)
        if (Objects.isNull(authorization)) {
            throw new AccountNotVerifiedException(AccountResponseCode.ACCOUNT_FORBIDDEN);
        }

        try {
            SignedJWT signedJWT = SignedJWT.parse(authorization);

            // jwt Secret 검증
            signedJWT.verify(new MACVerifier(jwtSecret));

            // jwt ExpiredTime 검증
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            LocalDateTime tokenExpiredTime = LocalDateTime.ofInstant(jwtClaimsSet.getExpirationTime().toInstant(), ZoneOffset.UTC);

            if (tokenExpiredTime.isBefore(LocalDateTime.now())) {  // 지금보다 과거이면 true
                throw new AccountTokenExpiredException(AccountResponseCode.ACCOUNT_TOKEN_EXPIRED);
            }

            // 인증된 사용자 request 속성 추가
            Long accountId = Long.parseLong(jwtClaimsSet.getSubject());

            Account account = accountRepository.findById(accountId)
                    .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));

            request.setAttribute("account", account);

        } catch (Exception e) {
            // 비정상 토큰인 경우
            throw new AccountTokenExpiredException(AccountResponseCode.ACCOUNT_TOKEN_EXPIRED);
        }
        return true;
    }

    public List<String> pathToInclude() {
        return PATH_TO_INCLUDE;
    }

    public List<String> pathToExclude() {
        return PATH_TO_EXCLUDE;
    }
}

package handong.whynot.filter;

import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.exception.account.AccountNotVerifiedException;
import handong.whynot.exception.account.AccountTokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Slf4j
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Value("${jwt.secretKey}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

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

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // 비정상 토큰인 경우
            throw new AccountTokenExpiredException(AccountResponseCode.ACCOUNT_TOKEN_EXPIRED);
        }
    }

    /**
     * JWT 토큰 검증 예외 화이트리스트
     *
     * @param request
     * @return
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return StringUtils.equals(path, "/")
                || StringUtils.equals(path, "/v1/posts")
                || StringUtils.equals(path, "/v1/login")
                || StringUtils.equals(path, "/v1/sign-up")
                || StringUtils.equals(path, "/v1/check-email-token")
                || StringUtils.equals(path, "/v1/resend-token")
                || StringUtils.equals(path, "/v1/check-email-duplicate")
                || StringUtils.equals(path, "/v1/check-nickname-duplicate")
                || StringUtils.equals(path, "/v1/account/login-state")
                || StringUtils.equals(path, "/v2/sign-in")
                ;
    }
}
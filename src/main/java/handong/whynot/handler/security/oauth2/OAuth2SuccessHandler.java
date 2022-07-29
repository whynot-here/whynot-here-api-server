package handong.whynot.handler.security.oauth2;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.TokenResponseDTO;
import handong.whynot.dto.account.UserAccount;
import handong.whynot.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import handong.whynot.service.SignInTokenGenerator;
import handong.whynot.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static handong.whynot.repository.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.security.oauth2.redirect-uri}")
    public String CLIENT_REDIRECT_URI;

    private final SignInTokenGenerator signInTokenGenerator;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        final Account account = ((UserAccount) authentication.getPrincipal()).getAccount();

        // JWT 응답
        String accessToken = signInTokenGenerator.accessToken(account.getId());
        String refreshToken = signInTokenGenerator.refreshToken(account.getId());

        final TokenResponseDTO token = TokenResponseDTO.of(account, accessToken, refreshToken);

        // RedirectURL 만들기
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        redirectUri.ifPresent(clientURL -> CLIENT_REDIRECT_URI = clientURL);

        // 응답값 생성
        final String targetUrl = UriComponentsBuilder.fromUriString(CLIENT_REDIRECT_URI)
                .queryParam("accessToken", token.getAccessToken())
                .queryParam("refreshToken", token.getRefreshToken())
                .build().toUriString();

        // 응답이 이미 클라이언트에 커밋되었는지 확인
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}

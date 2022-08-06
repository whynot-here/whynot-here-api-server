package handong.whynot.handler.security.oauth2;

import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import handong.whynot.util.CookieUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
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
public class OAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${spring.security.oauth2.redirect-uri}")
    public String CLIENT_REDIRECT_URI;

    @Value("${spring.security.oauth2.information-uri-essential}")
    public String INFORMATION_URI_ESSENTIAL;

    @Value("${spring.security.oauth2.information-uri-email}")
    public String INFORMATION_URI_EMAIL;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        String targetUrl = "";
        String errorCode = (((OAuth2AuthenticationException)exception).getError()).getErrorCode();

        // 필수 정보가 누락된 경우 안내 페이지
        if (StringUtils.equals(AccountResponseCode.ACCOUNT_OAUTH2_NOT_PROVIDED_VALUE.getStatusCode().toString(), errorCode)) {
            targetUrl = UriComponentsBuilder.fromUriString(INFORMATION_URI_ESSENTIAL)
                    .build().toUriString();
        }

        // 중복 이메일이 존재하는 경우 안내 페이지
        else if (StringUtils.equals(AccountResponseCode.ACCOUNT_OAUTH2_EXIST_SAME_EMAIL.getStatusCode().toString(), errorCode)) {
            targetUrl = UriComponentsBuilder.fromUriString(INFORMATION_URI_EMAIL)
                    .build().toUriString();
        }

        // 프론트에서 redirect uri 받지 않은 경우
        else {
            Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                    .map(Cookie::getValue);
            redirectUri.ifPresent(clientURL -> CLIENT_REDIRECT_URI = clientURL);

            targetUrl = UriComponentsBuilder.fromUriString(CLIENT_REDIRECT_URI)
                    .queryParam("error", exception.getLocalizedMessage())
                    .build().toUriString();
        }

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

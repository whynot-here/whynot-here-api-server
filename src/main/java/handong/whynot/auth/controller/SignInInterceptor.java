package handong.whynot.auth.controller;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SignInInterceptor implements HandlerInterceptor {

    private static final List<String> PATH_TO_INCLUDE = List.of(
        "/v2/need/**"
    );

    private static final List<String> PATH_TO_EXCLUDE = List.of(
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = ((UserAccount) authentication.getPrincipal()).getAccount();

        request.setAttribute("account", account);

        return true;
    }

    public List<String> pathToInclude() {
        return PATH_TO_INCLUDE;
    }

    public List<String> pathToExclude() {
        return PATH_TO_EXCLUDE;
    }
}

package handong.whynot.auth.controller;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.UserAccount;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SignInInterceptor implements HandlerInterceptor {

    private final AccountRepository accountRepository;

    // 사용자 정보가 필요한 API
    private static final List<String> PATH_TO_INCLUDE = List.of(
            "/v2/account/info"
    );

    private static final List<String> PATH_TO_EXCLUDE = List.of(
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Account account = accountRepository.findById(Long.valueOf(authentication.getName()))
                        .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));

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


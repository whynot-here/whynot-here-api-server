package handong.whynot.common;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.SignUpDTO;
import handong.whynot.dto.account.UserAccount;
import handong.whynot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Autowired
    private AccountService accountService;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        String nickname = customUser.username();

        Account account = Account.builder()
                .nickname(nickname)
                .email(nickname + "@email.com")
                .password("12345678")
                .build();

        account.completeSignUp();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new UserAccount(account),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
package handong.whynot.dto.account;

import handong.whynot.domain.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserAccount extends User implements OAuth2User {

    private Account account;
    private String accountId;
    private Map<String, Object> attributes;

    public UserAccount(Account account) {
        super(account.getNickname(), account.getPassword() == null ? "" : account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }

    public UserAccount(String accountId) {
        super(accountId, "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.accountId = accountId;
    }

    @Override
    public String getName() {
        return accountId;
    }
}

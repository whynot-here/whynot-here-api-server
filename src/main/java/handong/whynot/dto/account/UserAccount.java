package handong.whynot.dto.account;

import handong.whynot.domain.Account;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserAccount extends User {

    private Account account;
    private String accountId;

    public UserAccount(Account account) {
        super(account.getNickname(), account.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.account = account;
    }

    public UserAccount(String accountId) {
        super(accountId, "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.accountId = accountId;
    }
}

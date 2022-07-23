package handong.whynot.repository;

import handong.whynot.domain.Account;
import handong.whynot.domain.AuthType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    Account findByEmail(String email);

    Account findByNickname(String nickname);
    Optional<Account> findByEmailAndAuthType(String email, AuthType authType);


}

package handong.whynot.repository;

import handong.whynot.domain.Account;
import handong.whynot.domain.AccountRole;
import handong.whynot.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    void deleteByAccountAndRole(Account account, Role role);
}

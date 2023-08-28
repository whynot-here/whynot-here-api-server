package handong.whynot.repository;

import handong.whynot.domain.BlockAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlockAccountRepository extends JpaRepository<BlockAccount, Long> {
  List<BlockAccount> findAllByAccountId(Long accountId);
}

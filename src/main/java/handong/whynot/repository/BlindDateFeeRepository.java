package handong.whynot.repository;

import handong.whynot.domain.BlindDateFee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlindDateFeeRepository extends JpaRepository<BlindDateFee, Long> {
  BlindDateFee findByAccountId(Long accountId);
}

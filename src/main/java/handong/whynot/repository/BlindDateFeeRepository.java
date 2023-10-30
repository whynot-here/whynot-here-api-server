package handong.whynot.repository;

import handong.whynot.domain.BlindDateFee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlindDateFeeRepository extends JpaRepository<BlindDateFee, Long> {
  BlindDateFee findByAccountId(Long accountId);
  Optional<BlindDateFee> findByAccountIdAndSeasonAndUseYn(Long id, Integer season, String useYn);
  List<BlindDateFee> findAllBySeason(Integer season);
}

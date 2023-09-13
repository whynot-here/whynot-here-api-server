package handong.whynot.repository;

import handong.whynot.domain.BlindDateSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlindDateSummaryRepository extends JpaRepository<BlindDateSummary, Long> {
  BlindDateSummary findBySeason(Integer season);
}

package handong.whynot.repository;

import handong.whynot.domain.MatchingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingHistoryRepository extends JpaRepository<MatchingHistory, Long> {
}

package handong.whynot.repository;

import handong.whynot.domain.FriendMatchingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendMatchingHistoryRepository extends JpaRepository<FriendMatchingHistory, Long> {
  List<FriendMatchingHistory> findAllBySeason(Integer season);
}

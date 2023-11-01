package handong.whynot.repository;

import handong.whynot.domain.Account;
import handong.whynot.domain.FriendMeeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendMeetingRepository extends JpaRepository<FriendMeeting, Long> {
  Boolean existsByAccountIdAndSeason(Long accountId, Integer season);
  List<FriendMeeting> findAllBySeason(Integer season);
  Optional<FriendMeeting> findByAccountAndSeason(Account account, Integer season);
}

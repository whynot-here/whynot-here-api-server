package handong.whynot.repository;

import handong.whynot.domain.FriendMeeting;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FriendMeetingForCacheRepository {

  private final FriendMeetingRepository friendMeetingRepository;

  @Cacheable(value="FriendMatchedAccountList", key="'FriendMatchedAccountList'")
  public List<Long> getMatchedAccountListByCache(Integer season) {
    List<FriendMeeting> friendMeetingList = friendMeetingRepository.findAllBySeason(season);

    return friendMeetingList.stream()
      .filter(FriendMeeting::getIsReveal)
      .map(it -> it.getAccount().getId())
      .collect(Collectors.toList());
  }
}

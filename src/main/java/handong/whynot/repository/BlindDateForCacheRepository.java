package handong.whynot.repository;

import handong.whynot.domain.Account;
import handong.whynot.domain.BlindDate;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BlindDateForCacheRepository {

  private final BlindDateRepository blindDateRepository;

  @Cacheable(value="MatchedAccountList", key="'MatchedAccountList'")
  public List<Long> getMatchedAccountListByCache(Integer season) {
    List<BlindDate> blindDateList = blindDateRepository.findAllBySeason(season);

    return blindDateList.stream()
      .filter(BlindDate::getIsReveal)
      .map(it -> it.getAccount().getId())
      .collect(Collectors.toList());
  }
}

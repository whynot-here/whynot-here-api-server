package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.domain.BlindDate;
import handong.whynot.domain.ExcludeCond;
import handong.whynot.dto.blind_date.BlindDateRequestDTO;
import handong.whynot.dto.blind_date.BlindDateResponseCode;
import handong.whynot.exception.blind_date.BlindDateDuplicatedException;
import handong.whynot.exception.blind_date.BlindDateNotAuthenticatedException;
import handong.whynot.repository.BlindDateRepository;
import handong.whynot.repository.ExcludeCondRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlindDateService {

  private final BlindDateRepository blindDateRepository;
  private final ExcludeCondRepository excludeCondRepository;
  private final MobilePushService mobilePushService;

  @Transactional
  public void createBlindDate(BlindDateRequestDTO request, Account account) {
    // 1. 학생증 인증이 된 사용자인지 확인
    if (! account.isAuthenticated()) {
      throw new BlindDateNotAuthenticatedException(BlindDateResponseCode.BLIND_DATE_NOT_AUTHENTICATED);
    };

    // 2. (진행 중인 시즌 기간) 소개팅에 지원한 적이 있는지 확인
    if (isDuplicatedApply(account, request.getSeason())) {
      throw new BlindDateDuplicatedException(BlindDateResponseCode.BLIND_DATE_DUPLICATED);
    }

    // 3. 소개팅 지원
    BlindDate blindDate = BlindDate.of(request, account);
    blindDateRepository.save(blindDate);

    // exclude condition 저장
    List<ExcludeCond> excludeConds = request.getExcludeCondList().stream()
      .peek(it -> it.setBlindDate(blindDate))
      .collect(Collectors.toList());
    excludeCondRepository.saveAll(excludeConds);

    // todo: 4. 확인 모바일 푸시
  }

  private Boolean isDuplicatedApply(Account account, Integer season) {
    Optional<BlindDate> blindDate = blindDateRepository.findByAccountAndSeason(account, season);
    return blindDate.isPresent();
  }

  public Long getApplicantsCntBySeason(Integer season) {
    return blindDateRepository.countBySeason(season);
  }

  public Boolean getIsParticipatedBySeason(Integer season, Account account) {
    return blindDateRepository.existsByAccountAndSeason(account, season);
  }
}

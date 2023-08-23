package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.domain.BlindDate;
import handong.whynot.domain.ExcludeCond;
import handong.whynot.domain.MatchingHistory;
import handong.whynot.dto.blind_date.BlindDateRequestDTO;
import handong.whynot.dto.blind_date.BlindDateResponseCode;
import handong.whynot.dto.blind_date.BlindDateResponseDTO;
import handong.whynot.exception.blind_date.*;
import handong.whynot.repository.BlindDateRepository;
import handong.whynot.repository.ExcludeCondRepository;
import handong.whynot.repository.MatchingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlindDateService {

  private final BlindDateRepository blindDateRepository;
  private final ExcludeCondRepository excludeCondRepository;
  private final MobilePushService mobilePushService;
  private final MatchingHistoryRepository matchingHistoryRepository;

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

  public BlindDateResponseDTO getMatchingResultBySeason(Integer season, Account account) {
    // 소개팅 지원 확인
    BlindDate blindDate = blindDateRepository.findByAccountAndSeason(account, season)
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    // 매칭 대상 여부 확인
    if (Objects.isNull(blindDate.getMatchingBlindDateId())) {
      throw new BlindDateNotMatchedException(BlindDateResponseCode.BLIND_DATE_NOT_MATCHED);
    }

    BlindDate matchedBlindDate = blindDateRepository.findById(blindDate.getMatchingBlindDateId())
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    return BlindDateResponseDTO.of(matchedBlindDate);
  }

  @Transactional
  public void submitApply(Boolean approval, Integer season, Account account) {
    // 소개팅 지원 확인
    BlindDate blindDate = blindDateRepository.findByAccountAndSeason(account, season)
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    // 지원 의사 업데이트
    blindDate.updateMatchingApproval(approval);
  }

  @Transactional
  public void createMatchingBySeason(Long maleId, Long femaleId, Integer season) {
    // 남자인지 확인
    BlindDate male = blindDateRepository.findById(maleId)
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));
    if (! StringUtils.equals(male.getGender(), "M")) {
      throw new InvalidMatchingException(BlindDateResponseCode.MATCHING_INVALID);
    }

    // 여자인지 확인
    BlindDate female = blindDateRepository.findById(femaleId)
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));
    if (! StringUtils.equals(female.getGender(), "F")) {
      throw new InvalidMatchingException(BlindDateResponseCode.MATCHING_INVALID);
    }

    // 이미 매칭이 된 경우
    if (Objects.nonNull(male.getMatchingBlindDateId())) {
      throw new InvalidMatchingException(BlindDateResponseCode.MATCHING_INVALID);
    }

    // 매칭 업데이트
    male.updateMatchingBlindDate(femaleId);
    female.updateMatchingBlindDate(maleId);

    MatchingHistory history = MatchingHistory.builder()
      .maleId(maleId)
      .femaleId(femaleId)
      .season(season)
      .build();
    matchingHistoryRepository.save(history);
  }
}

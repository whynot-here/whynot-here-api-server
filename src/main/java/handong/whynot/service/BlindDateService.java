package handong.whynot.service;

import handong.whynot.domain.*;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.admin.AdminBlindDateResponseDTO;
import handong.whynot.dto.blind_date.BlindDateRequestDTO;
import handong.whynot.dto.blind_date.BlindDateResponseCode;
import handong.whynot.dto.blind_date.BlindDateResponseDTO;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.exception.blind_date.*;
import handong.whynot.repository.*;
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
  private final BlindDateForCacheRepository blindDateForCacheRepository;
  private final ExcludeCondRepository excludeCondRepository;
  private final MobilePushService mobilePushService;
  private final MatchingHistoryRepository matchingHistoryRepository;
  private final AccountRepository accountRepository;
  private final BlindDateSummaryRepository blindDateSummaryRepository;

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

   // 상대방 이름 한글자 masking
    if (matchedBlindDate.getName().length() >= 2) {
      matchedBlindDate.setName(matchedBlindDate.getName().charAt(0) + "*" + matchedBlindDate.getName().substring(2));
    }

    // 상대방 프로필 이미지 조회
    Account matchedAccount = accountRepository.findById(matchedBlindDate.getAccount().getId())
      .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));

    // 공용 링크는 Female 링크 사용
    if (StringUtils.equals(blindDate.getGender(), "F")) {
      matchedBlindDate.setKakaoLink(blindDate.getKakaoLink());
    }

    return BlindDateResponseDTO.of(matchedBlindDate, matchedAccount.getProfileImg(), blindDate.getName());
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

  public List<AdminBlindDateResponseDTO> getBlindDateListBySeason(Integer season) {
    return blindDateRepository.findAllBySeason(season).stream()
      .map(AdminBlindDateResponseDTO::of)
      .collect(Collectors.toList());
  }


  public void noticeMatchingInfo(Integer season) {
    List<BlindDate> blindDateList = blindDateRepository.findAllBySeason(season);
    List<Account> accountList = blindDateList.stream()
      .filter(it -> Objects.nonNull(it.getMatchingBlindDateId()))
      .map(BlindDate::getAccount)
      .collect(Collectors.toList());

    // 사용자 매칭 결과 노출 ON
    for (BlindDate blindDate : blindDateList) {
      blindDate.setIsReveal(true);
    }

    mobilePushService.pushMatchingInfo(accountList);
  }

  public Boolean getIsRevealResultBySeason(Integer season, Account account) {
    List<Long> accountList = blindDateForCacheRepository.getMatchedAccountListByCache(season);

    return accountList.contains(account.getId());
  }

  public BlindDateSummary getMatchingResultSummary(Integer season) {
    return blindDateSummaryRepository.findBySeason(season);
  }
}

package handong.whynot.service;

import handong.whynot.domain.*;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.admin.AdminBlindDateResponseDTO;
import handong.whynot.dto.blind_date.*;
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
  private final MatchingHistoryService matchingHistoryService;
  private final AccountRepository accountRepository;
  private final BlindDateSummaryRepository blindDateSummaryRepository;
  private final BlindDateFeeRepository blindDateFeeRepository;
  private final BlindDateImageLinkRepository blindDateImageLinkRepository;

  @Transactional
  public void createBlindDate(Integer season, Account account) {
    // 1. 학생증 인증이 된 사용자인지 확인
    if (! account.isAuthenticated()) {
      throw new BlindDateNotAuthenticatedException(BlindDateResponseCode.BLIND_DATE_NOT_AUTHENTICATED);
    };

    // 2. (진행 중인 시즌 기간) 소개팅에 지원한 적이 있는지 확인
    if (isDuplicatedApply(account, season)) {
      throw new BlindDateDuplicatedException(BlindDateResponseCode.BLIND_DATE_DUPLICATED);
    }

    // 3. 소개팅 지원
    BlindDate blindDate = BlindDate.of(season, account);
    blindDateRepository.save(blindDate);
  }

  @Transactional
  public void updateBlindDate(BlindDateRequestDTO request, Account account) {
    BlindDate blindDate = blindDateRepository.findByAccountAndSeason(account, request.getSeason())
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    blindDate.updateBlindDate(request);

    // 1. exclude condition 저장
    saveExcludeConds(blindDate, request.getExcludeCondList());

    // 2. image links 저장
    saveImageLinks(account, blindDate, request.getImageLinks());
  }

  private void saveExcludeConds(BlindDate blindDate, List<ExcludeCond> excludeCondList) {
    excludeCondRepository.deleteAllByBlindDate(blindDate);

    List<ExcludeCond> excludeConds = excludeCondList.stream()
      .peek(it -> it.setBlindDate(blindDate))
      .collect(Collectors.toList());
    excludeCondRepository.saveAll(excludeConds);
  }

  private void saveImageLinks(Account account, BlindDate blindDate, List<String> links) {
    blindDateImageLinkRepository.deleteAllByBlindDate(blindDate);

    List<BlindDateImageLink> imageLinks = links.stream()
      .map(it -> BlindDateImageLink.of(account.getId(), blindDate, it))
      .collect(Collectors.toList());
    blindDateImageLinkRepository.saveAll(imageLinks);
  }

  private Boolean isDuplicatedApply(Account account, Integer season) {
    Optional<BlindDate> blindDate = blindDateRepository.findByAccountAndSeason(account, season);
    return blindDate.isPresent();
  }

  public Long getApplicantsCntBySeason(Integer season) {
    return blindDateRepository.countBySeason(season);
  }

  public Boolean getIsParticipatedBySeason(Integer season, Account account) {

    return blindDateFeeRepository.existsByAccountIdAndSeasonAndUseYn(account.getId(), season, "Y");
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

    // reveal 여부 확인
    if (! blindDate.getIsReveal()) {
      throw new BlindDateNotOpenedException(BlindDateResponseCode.REVEAL_FAIL);
    }

   // 상대방 이름 한글자 masking
    if (matchedBlindDate.getName().length() >= 2) {
      matchedBlindDate.setName(matchedBlindDate.getName().charAt(0) + "*" + matchedBlindDate.getName().substring(2));
    }

    // 상대방 프로필 이미지 조회
    Account matchedAccount = accountRepository.findById(matchedBlindDate.getAccount().getId())
      .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));

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
  public void createMatching(Long maleId, Long femaleId) {
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
      .season(male.getSeason())
      .isApproved(false)
      .build();
    matchingHistoryRepository.save(history);
  }

  public List<AdminBlindDateResponseDTO> getBlindDateListBySeason(Integer season) {
    return blindDateRepository.findAllBySeason(season).stream()
      .map(AdminBlindDateResponseDTO::of)
      .collect(Collectors.toList());
  }


  public void noticeMatchingInfoBySeason(Integer season) {
    List<BlindDate> blindDateList = blindDateRepository.findAllBySeason(season);
    // 사용자 매칭 결과 노출 ON
    for (BlindDate blindDate : blindDateList) {
      blindDate.setIsReveal(true);
    }

    // 매칭 성공 사용자 push
    List<Account> accountSuccessList = blindDateList.stream()
      .filter(it -> Objects.nonNull(it.getMatchingBlindDateId()))
      .map(BlindDate::getAccount)
      .collect(Collectors.toList());

    mobilePushService.pushMatchingSuccess(accountSuccessList);

    // 매칭 실패한 사용자 push
    List<Account> accountFailList = blindDateList.stream()
      .filter(it -> Objects.isNull(it.getMatchingBlindDateId()))
      .map(BlindDate::getAccount)
      .collect(Collectors.toList());

    mobilePushService.pushMatchingFail(accountFailList);
  }

  public Boolean getIsRevealResultBySeason(Integer season, Account account) {
    List<Long> accountList = blindDateForCacheRepository.getMatchedAccountListByCache(season);

    return accountList.contains(account.getId());
  }

  public BlindDateSummary getMatchingResultSummary(Integer season) {
    return blindDateSummaryRepository.findBySeason(season);
  }

  @Transactional
  public void createBlindDateFee(Account account, BlindDateFeeRequestDTO dto) {
    // 신청한 내역이 있는지 확인
    if (isDuplicatedBlindDateFee(account, dto.getSeason())) {
      throw new BlindDateFeeDuplicatedException(BlindDateResponseCode.BLIND_DATE_FEE_DUPLICATED);
    }

    BlindDateFee dateFee = BlindDateFee.of(account.getId(), dto);
    blindDateFeeRepository.save(dateFee);
  }

  private Boolean isDuplicatedBlindDateFee(Account account, Integer season) {
    Optional<BlindDateFee> blindDateFee = blindDateFeeRepository.findByAccountIdAndSeasonAndUseYn(account.getId(), season, "Y");
    return blindDateFee.isPresent();
  }

  public Boolean getFeeIsSubmitted(Account account, Integer season) {

    BlindDateFee blindDateFee = blindDateFeeRepository.findByAccountIdAndSeasonAndUseYn(account.getId(), season, "Y")
      .orElseThrow(() -> new BlindDateFeeNotFoundException(BlindDateResponseCode.BLIND_DATE_FEE_READ_FAIL));
    return blindDateFee.getIsSubmitted();
  }

  public Boolean getBlindDateSubmitted(Account account, Integer season) {
    BlindDate blindDate = blindDateRepository.findByAccountAndSeason(account, season)
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    return blindDate.getIsSubmitted();
  }

  @Transactional
  public void finishEditing(Account account, Integer season) {
    BlindDate blindDate = blindDateRepository.findByAccountAndSeason(account, season)
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    blindDate.setIsSubmitted(true);
  }

  @Transactional
  public void createMatchingImage(Account account, Integer season, String link) {
    BlindDate blindDate = blindDateRepository.findByAccountAndSeason(account, season)
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    matchingHistoryService.updateImageLink(blindDate, link);
  }

  public MatchingImageResponseDTO getMatchingImageBySeason(Account account, Integer season) {
    BlindDate blindDate = blindDateRepository.findByAccountAndSeason(account, season)
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    String imageLink = matchingHistoryService.getMatchingImageByBlindDate(blindDate);
    return MatchingImageResponseDTO.builder()
      .imageLink(imageLink)
      .build();
  }

  public Boolean getMatchingApprovedBySeason(Account account, Integer season) {
    BlindDate blindDate = blindDateRepository.findByAccountAndSeason(account, season)
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    return matchingHistoryService.getMatchingApprovedByBlindDate(blindDate);
  }

  @Transactional
  public void applyRetryBySeason(Account account, Integer season, String reason) {
    BlindDate blindDate = blindDateRepository.findByAccountAndSeason(account, season)
      .orElseThrow(() -> new BlindDateNotFoundException(BlindDateResponseCode.BLIND_DATE_READ_FAIL));

    blindDate.setIsRetry(true);
    blindDate.setRetryReason(reason);
  }
}

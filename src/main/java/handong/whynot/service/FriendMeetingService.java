package handong.whynot.service;

import handong.whynot.domain.*;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.admin.AdminBlindDateResponseDTO;
import handong.whynot.dto.admin.AdminFriendMeetingResponseDTO;
import handong.whynot.dto.blind_date.*;
import handong.whynot.dto.friend_meeting.FriendMeetingRequestDTO;
import handong.whynot.dto.friend_meeting.FriendMeetingResponseCode;
import handong.whynot.dto.friend_meeting.FriendMeetingResponseDTO;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.exception.blind_date.*;
import handong.whynot.exception.friend_meeting.FriendMeetingDuplicatedException;
import handong.whynot.exception.friend_meeting.FriendMeetingNotFoundException;
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
public class FriendMeetingService {

  private final FriendMeetingRepository friendMeetingRepository;
  private final FriendMeetingForCacheRepository friendMeetingForCacheRepository;
  private final AccountRepository accountRepository;
  private final MobilePushService mobilePushService;
  private final BlindDateFeeRepository blindDateFeeRepository;
  private final FriendMatchingHistoryRepository friendMatchingHistoryRepository;

  @Transactional
  public void createFriendMeeting(FriendMeetingRequestDTO request, Account account) {
    // 1. 학생증 인증이 된 사용자인지 확인
    if (! account.isAuthenticated()) {
      throw new BlindDateNotAuthenticatedException(BlindDateResponseCode.BLIND_DATE_NOT_AUTHENTICATED);
    };

    // 2. (진행 중인 시즌 기간) 친구 만남에 지원한 적이 있는지 확인
    if (isDuplicatedApply(account, request.getSeason())) {
      throw new FriendMeetingDuplicatedException(FriendMeetingResponseCode.FRIEND_MEETING_DUPLICATED);
    }

    // 3. 소개팅 지원한 적이 있는지 확인
    if (isDuplicatedBlindDate(account, request.getSeason())) {
      throw new BlindDateDuplicatedException(BlindDateResponseCode.BLIND_DATE_DUPLICATED);
    }

    // 4. 친구 만남 지원
    FriendMeeting friendMeeting = FriendMeeting.of(request, account);
    friendMeetingRepository.save(friendMeeting);
  }

  private Boolean isDuplicatedApply(Account account, Integer season) {
    Optional<FriendMeeting> friendMeeting = friendMeetingRepository.findByAccountAndSeason(account, season);
    return friendMeeting.isPresent();
  }

  private Boolean isDuplicatedBlindDate(Account account, Integer season) {
    Optional<BlindDateFee> blindDateFee = blindDateFeeRepository.findByAccountIdAndSeasonAndUseYn(account.getId(), season, "Y");
    return blindDateFee.isPresent();
  }

  public Boolean getIsParticipatedBySeason(Integer season, Account account) {

    return friendMeetingRepository.existsByAccountIdAndSeason(account.getId(), season);
  }

  public FriendMeetingResponseDTO getMatchingResultBySeason(Integer season, Account account) {
    // 소개팅 지원 확인
    FriendMeeting friendMeeting = friendMeetingRepository.findByAccountAndSeason(account, season)
      .orElseThrow(() -> new FriendMeetingNotFoundException(FriendMeetingResponseCode.FRIEND_MEETING_READ_FAIL));

    // 매칭 대상 여부 확인
    if (Objects.isNull(friendMeeting.getMatchingFriendMeetingId())) {
      throw new FriendMeetingNotFoundException(FriendMeetingResponseCode.FRIEND_MEETING_READ_FAIL);
    }

    FriendMeeting matchedFriendMeeting = friendMeetingRepository.findById(friendMeeting.getMatchingFriendMeetingId())
      .orElseThrow(() -> new FriendMeetingNotFoundException(FriendMeetingResponseCode.FRIEND_MEETING_READ_FAIL));

    // reveal 여부 확인
    if (! friendMeeting.getIsReveal()) {
      throw new FriendMeetingNotFoundException(FriendMeetingResponseCode.REVEAL_FAIL);
    }

   // 상대방 이름 한글자 masking
    if (matchedFriendMeeting.getName().length() >= 2) {
      matchedFriendMeeting.setName(matchedFriendMeeting.getName().charAt(0) + "*" + matchedFriendMeeting.getName().substring(2));
    }

    // 상대방 프로필 이미지 조회
    Account matchedAccount = accountRepository.findById(matchedFriendMeeting.getAccount().getId())
      .orElseThrow(() -> new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));

    return FriendMeetingResponseDTO.of(matchedFriendMeeting, matchedAccount.getProfileImg(), friendMeeting.getName());
  }

  public Boolean getIsRevealResultBySeason(Integer season, Account account) {
    List<Long> accountList = friendMeetingForCacheRepository.getMatchedAccountListByCache(season);

    return accountList.contains(account.getId());
  }
  public Boolean getIsReportMannersBySeason(Integer season, Account account) {
    FriendMeeting friendMeeting = friendMeetingRepository.findByAccountAndSeason(account, season)
      .orElseThrow(() -> new FriendMeetingNotFoundException(FriendMeetingResponseCode.FRIEND_MEETING_READ_FAIL));

    return StringUtils.isNotBlank(friendMeeting.getMannersReason());
  }

  @Transactional
  public void reportManners(Integer season, Account account, MannerReportRequestDTO request) {
    FriendMeeting friendMeeting = friendMeetingRepository.findByAccountAndSeason(account, season)
      .orElseThrow(() -> new FriendMeetingNotFoundException(FriendMeetingResponseCode.FRIEND_MEETING_READ_FAIL));

    friendMeeting.setMannersReason(request.getReason());
    friendMeeting.setMannersReasonDesc(request.getReasonDesc());

    // todo: 상대방 비매너 신고 안내 (푸시 알림)
    // 1안) 신고내용을 관리자에게 알림 보낸다,  2안) 신고내용을 상대방에게 알림 보낸다
  }

  public void createFriendMeetingMatching(Long friend1, Long friend2) {
    FriendMeeting friendMeeting1 = friendMeetingRepository.findById(friend1)
      .orElseThrow(() -> new FriendMeetingNotFoundException(FriendMeetingResponseCode.FRIEND_MEETING_READ_FAIL));

    FriendMeeting friendMeeting2 = friendMeetingRepository.findById(friend2)
      .orElseThrow(() -> new FriendMeetingNotFoundException(FriendMeetingResponseCode.FRIEND_MEETING_READ_FAIL));

    // 이미 매칭이 된 경우
    if (Objects.nonNull(friendMeeting1.getMatchingFriendMeetingId()) ||
      Objects.nonNull(friendMeeting2.getMatchingFriendMeetingId())
    ) {
      throw new InvalidMatchingException(BlindDateResponseCode.MATCHING_INVALID);
    }

    // 매칭 업데이트
    friendMeeting1.updateMatchingFriendMeeting(friend2);
    friendMeeting2.updateMatchingFriendMeeting(friend1);

    FriendMatchingHistory history = FriendMatchingHistory.builder()
      .friendId1(friend1)
      .friendId2(friend2)
      .season(friendMeeting1.getSeason())
      .build();
    friendMatchingHistoryRepository.save(history);
  }

  public List<AdminFriendMeetingResponseDTO> getFriendMeetingListBySeason(Integer season) {

    return friendMeetingRepository.findAllBySeason(season).stream()
      .map(AdminFriendMeetingResponseDTO::of)
      .collect(Collectors.toList());
  }

  public void noticeFriendMatchingInfoBySeason(Integer season) {
    List<FriendMeeting> friendMeetingList = friendMeetingRepository.findAllBySeason(season);
    noticeResult(friendMeetingList);
  }

  private void noticeResult(List<FriendMeeting> friendMeetingList) {
    // 사용자 매칭 결과 노출 ON
    for (FriendMeeting friendMeeting : friendMeetingList) {
      friendMeeting.setIsReveal(true);
    }

    // 매칭 성공 사용자 push
    List<Account> accountSuccessList = friendMeetingList.stream()
      .filter(it -> Objects.nonNull(it.getMatchingFriendMeetingId()))
      .map(FriendMeeting::getAccount)
      .collect(Collectors.toList());

    mobilePushService.pushMatchingSuccess(accountSuccessList);

    // 매칭 실패한 사용자 push
    List<Account> accountFailList = friendMeetingList.stream()
      .filter(it -> Objects.isNull(it.getMatchingFriendMeetingId()))
      .map(FriendMeeting::getAccount)
      .collect(Collectors.toList());

    mobilePushService.pushMatchingFail(accountFailList);
  }
}

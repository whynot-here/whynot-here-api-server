package handong.whynot.service;

import handong.whynot.domain.BlindDate;
import handong.whynot.domain.FriendMatchingHistory;
import handong.whynot.domain.FriendMeeting;
import handong.whynot.domain.MatchingHistory;
import handong.whynot.dto.admin.AdminFriendMatchingResponseDTO;
import handong.whynot.dto.blind_date.BlindDateResponseCode;
import handong.whynot.dto.friend_meeting.FriendMeetingResponseCode;
import handong.whynot.exception.blind_date.BlindDateNotFoundException;
import handong.whynot.exception.blind_date.MatchingNotFoundException;
import handong.whynot.exception.friend_meeting.FriendMeetingNotFoundException;
import handong.whynot.repository.FriendMatchingHistoryRepository;
import handong.whynot.repository.FriendMeetingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendMatchingHistoryService {
  private final FriendMatchingHistoryRepository friendMatchingHistoryRepository;
  private final FriendMeetingRepository friendMeetingRepository;

  public List<AdminFriendMatchingResponseDTO> getFriendMatchingListBySeason(Integer season) {

    return friendMatchingHistoryRepository.findAllBySeason(season).stream()
      .map(AdminFriendMatchingResponseDTO::of)
      .collect(Collectors.toList());
  }

  public void deleteFriendMatching(Long matchingId) {
    FriendMatchingHistory matchingHistory = friendMatchingHistoryRepository.findById(matchingId)
      .orElseThrow(() -> new MatchingNotFoundException(FriendMeetingResponseCode.MATCHING_READ_FAIL));

    // 매칭 업데이트
    FriendMeeting friend1 = friendMeetingRepository.findById(matchingHistory.getFriendId1())
      .orElseThrow(() -> new FriendMeetingNotFoundException(FriendMeetingResponseCode.FRIEND_MEETING_READ_FAIL));
    FriendMeeting friend2 = friendMeetingRepository.findById(matchingHistory.getFriendId2())
      .orElseThrow(() -> new FriendMeetingNotFoundException(FriendMeetingResponseCode.FRIEND_MEETING_READ_FAIL));

    friend1.updateMatchingFriendMeeting(null);
    friend2.updateMatchingFriendMeeting(null);

    friendMatchingHistoryRepository.deleteById(matchingId);
  }
}

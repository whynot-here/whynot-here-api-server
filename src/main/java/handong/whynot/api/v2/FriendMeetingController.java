package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.dto.blind_date.*;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.dto.friend_meeting.FriendMeetingRequestDTO;
import handong.whynot.dto.friend_meeting.FriendMeetingResponseCode;
import handong.whynot.dto.friend_meeting.FriendMeetingResponseDTO;
import handong.whynot.service.AccountService;
import handong.whynot.service.FriendMeetingService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/friend-meeting")
public class FriendMeetingController {

  private final FriendMeetingService friendMeetingService;
  private final AccountService accountService;

  @Operation(summary = "친구 만남 지원")
  @PostMapping("")
  @ResponseStatus(CREATED)
  public ResponseDTO createFriendMeeting(@RequestBody FriendMeetingRequestDTO request) {

    Account account = accountService.getCurrentAccount();
    friendMeetingService.createFriendMeeting(request, account);

    return ResponseDTO.of(FriendMeetingResponseCode.FRIEND_MEETING_CREATED_OK);
  }

  @Operation(summary = "친구 만남 지원 여부 조회")
  @GetMapping("/participation")
  public Boolean getIsParticipatedBySeason(@RequestParam Integer season) {
    Account account = accountService.getCurrentAccount();

    return friendMeetingService.getIsParticipatedBySeason(season, account);
  }

  @Operation(summary = "매칭 결과 노출 여부 조회")
  @GetMapping("/reveal-result")
  public Boolean getIsRevealResultBySeason(@RequestParam Integer season) {
    Account account = accountService.getCurrentAccount();

    return friendMeetingService.getIsRevealResultBySeason(season, account);
  }

  @Operation(summary = "매칭 후 상대방 정보 조회")
  @GetMapping("/matching-result")
  public FriendMeetingResponseDTO getMatchingResultBySeason(@RequestParam Integer season) {
    Account account = accountService.getCurrentAccount();

    return friendMeetingService.getMatchingResultBySeason(season, account);
  }

  @Operation(summary = "비매너 신고 여부 조회")
  @GetMapping("/manners")
  public Boolean getIsReportMannersBySeason(@RequestParam Integer season) {
    Account account = accountService.getCurrentAccount();

    return friendMeetingService.getIsReportMannersBySeason(season, account);
  }

  @Operation(summary = "비매너 신고")
  @PutMapping("/manners")
  public ResponseDTO reportMannersBySeason(@RequestParam Integer season,
                                           @RequestBody MannerReportRequestDTO request) {
    Account account = accountService.getCurrentAccount();
    friendMeetingService.reportManners(season, account, request);

    return ResponseDTO.of(FriendMeetingResponseCode.FRIEND_MEETING_REPORT_MANNERS_OK);
  }
}

package handong.whynot.dto.admin;

import handong.whynot.domain.FriendMeeting;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminFriendMeetingResponseDTO {

  // =========== 메타 정보 ===========
  private Long friendMeetingId;
  private Boolean matchedByAdmin;

  // =========== 내 정보 ===========
  private String name;
  private String gender;
  private Integer myAge;
  private Integer myHeight;
  private String mySmoke;
  private String myDrink;
  private String myLocation;
  private String myLocationDesc;
  private String myHobby;
  private String myHobbyDesc;
  private String comment;
  private String kakaoLink;
  private String mannersReason;
  private String mannersReasonDesc;

  public static AdminFriendMeetingResponseDTO of(FriendMeeting friendMeeting) {
    return builder()
      // =========== 메타 정보 ===========
      .friendMeetingId(friendMeeting.getId())
      .matchedByAdmin(friendMeeting.getMatchingFriendMeetingId() != null)
      // =========== 내 정보 ===========
      .name(friendMeeting.getName())
      .gender(friendMeeting.getGender())
      .myAge(friendMeeting.getMyAge())
      .myHeight(friendMeeting.getMyHeight())
      .mySmoke(friendMeeting.getMySmoke())
      .myDrink(friendMeeting.getMyDrink())
      .myLocation(friendMeeting.getMyLocation())
      .myLocationDesc(friendMeeting.getMyLocationDesc())
      .myHobby(friendMeeting.getMyHobby())
      .myHobbyDesc(friendMeeting.getMyHobbyDesc())
      .comment(friendMeeting.getComment())
      .kakaoLink(friendMeeting.getKakaoLink())
      .mannersReason(friendMeeting.getMannersReason())
      .mannersReasonDesc(friendMeeting.getMannersReasonDesc())
      .build();
  }
}

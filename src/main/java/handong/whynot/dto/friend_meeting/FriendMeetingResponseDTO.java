package handong.whynot.dto.friend_meeting;

import handong.whynot.domain.FriendMeeting;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendMeetingResponseDTO {

  // =========== 내 정보 ===========
  private String myName;

  // =========== 상대방 정보 ===========
  private String name;
  private String gender;
  private Integer age;
  private Integer height;
  private String smoke;
  private String drink;
  private String location;
  private String locationDesc;
  private String hobby;
  private String hobbyDesc;
  private String comment;
  private String kakaoLink;
  private String profileImg;

  public static FriendMeetingResponseDTO of(FriendMeeting friendMeeting, String profileImg, String myName) {
    return builder()
      .myName(myName)
      .name(friendMeeting.getName())
      .gender(friendMeeting.getGender())
      .age(friendMeeting.getMyAge())
      .height(friendMeeting.getMyHeight())
      .location(friendMeeting.getMyLocation())
      .locationDesc(friendMeeting.getMyLocationDesc())
      .hobby(friendMeeting.getMyHobby())
      .hobbyDesc(friendMeeting.getMyHobbyDesc())
      .comment(friendMeeting.getComment())
      .kakaoLink(friendMeeting.getComment())
      .profileImg(profileImg)
      .build();
  }
}

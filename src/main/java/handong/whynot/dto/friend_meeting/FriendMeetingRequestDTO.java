package handong.whynot.dto.friend_meeting;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendMeetingRequestDTO {
  // =========== 메타 정보 ===========
  private Integer season;

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
}

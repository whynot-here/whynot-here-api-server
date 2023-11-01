package handong.whynot.domain;

import handong.whynot.domain.common.BaseTimeEntity;
import handong.whynot.dto.friend_meeting.FriendMeetingRequestDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class FriendMeeting extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  // =========== 메타 정보 ===========
  @Column(name = "matching_friend_meeting_id")
  private Long matchingFriendMeetingId;

  @Column(name = "season")
  private Integer season;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_matched")
  private Boolean isMatched;

  @Column(name = "is_reveal")
  private Boolean isReveal;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "account_id")
  private Account account;

  // =========== 내 정보 ===========
  @Column(name = "name")
  private String name;

  @Column(name = "gender")
  private String gender;

  @Column(name = "my_age")
  private Integer myAge;

  @Column(name = "my_height")
  private Integer myHeight;

  @Column(name = "my_smoke")
  private String mySmoke;

  @Column(name = "my_drink")
  private String myDrink;

  @Column(name = "my_location")
  private String myLocation;

  @Column(name = "my_location_desc")
  private String myLocationDesc;

  @Column(name = "my_hobby")
  private String myHobby;

  @Column(name = "my_hobby_desc")
  private String myHobbyDesc;

  @Column(name = "comment")
  private String comment;

  @Column(name = "kakao_link")
  private String kakaoLink;

  @Column(name = "manners_reason")
  private String mannersReason;

  @Column(name = "manners_reason_desc")
  private String mannersReasonDesc;

  public static FriendMeeting of(FriendMeetingRequestDTO request, Account account) {
    return builder()
      // =========== 메타 정보 ===========
      .matchingFriendMeetingId(null)
      .isActive(false)
      .isMatched(false)
      .isReveal(false)
      .season(request.getSeason())
      .account(account)
      // =========== 내 정보 ===========
      .name(request.getName())
      .gender(request.getGender())
      .myAge(request.getMyAge())
      .myHeight(request.getMyHeight())
      .mySmoke(request.getMySmoke())
      .myDrink(request.getMyDrink())
      .myLocation(request.getMyLocation())
      .myLocationDesc(request.getMyLocationDesc())
      .myHobby(request.getMyHobby())
      .myHobbyDesc(request.getMyHobbyDesc())
      .comment(request.getComment())
      .kakaoLink(request.getKakaoLink())
      .build();
  }

  public void updateMatchingFriendMeeting(Long matchingId) {
    matchingFriendMeetingId = matchingId;
  }
}

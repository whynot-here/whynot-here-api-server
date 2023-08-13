package handong.whynot.domain;

import handong.whynot.domain.common.BaseTimeEntity;
import handong.whynot.dto.blind_date.BlindDateRequestDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class BlindDate extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "matching_blind_date_id")
  private Long matchingBlindDateId;

  @Column(name = "season")
  private Integer season;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_matched")
  private Boolean isMatched;

  @Column(name = "name")
  private String name;

  @Column(name = "gender")
  private String gender;

  @Column(name = "my_age")
  private Integer myAge;

  @Column(name = "favorite_age")
  private String favoriteAge;

  @Column(name = "date_style")
  private String dateStyle;

  @Column(name = "hobby")
  private String hobby;

  @Column(name = "faith")
  private String faith;

  @Column(name = "mbti")
  private String mbti;

  @Column(name = "smoke")
  private String smoke;

  @Column(name = "comment")
  private String comment;

  @Column(name = "kakao_link")
  private String kakaoLink;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  public static BlindDate of(BlindDateRequestDTO request, Account account) {
    return builder()
      .matchingBlindDateId(null)
      .isActive(false)
      .isMatched(false)
      .season(request.getSeason())
      .name(request.getName())
      .gender(request.getGender())
      .myAge(request.getMyAge())
      .favoriteAge(request.getFavoriteAge())
      .dateStyle(request.getDateStyle())
      .hobby(request.getHobby())
      .faith(request.getFaith())
      .mbti(request.getMbti())
      .smoke(request.getSmoke())
      .comment(request.getComment())
      .kakaoLink(request.getKakaoLink())
      .account(account)
      .build();
  }

  public void updateMatchingBlindDate(Boolean approval) {
    isActive = true;
    isMatched = approval;
  }
}

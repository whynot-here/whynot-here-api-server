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

  // =========== 메타 정보 ===========
  @Column(name = "matching_blind_date_id")
  private Long matchingBlindDateId;

  @Column(name = "season")
  private Integer season;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "is_matched")
  private Boolean isMatched;

  @Column(name = "is_reveal")
  private Boolean isReveal;

  @Column(name = "is_submitted")
  private Boolean isSubmitted;

  @Column(name = "is_retry")
  private Boolean isRetry;

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

  @Column(name = "my_drink")
  private String myDrink;

  @Column(name = "my_location")
  private String myLocation;

  @Column(name = "my_location_desc")
  private String myLocationDesc;

  @Column(name = "hobby")
  private String hobby;

  @Column(name = "hobby_desc")
  private String hobbyDesc;

  @Column(name = "my_contact_style")
  private String myContactStyle;

  @Column(name = "my_character")
  private String myCharacter;

  @Column(name = "date_style")
  private String dateStyle;

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

  @Column(name = "retry_reason")
  private String retryReason;

  @Column(name = "manners_reason")
  private String mannersReason;

  @Column(name = "manners_reason_desc")
  private String mannersReasonDesc;

  // =========== 선호하는 상대방 정보 ===========
  @Column(name = "favorite_age")
  private String favoriteAge;

  @Column(name = "favorite_height")
  private String favoriteHeight;

  @Column(name = "favorite_smoke")
  private String favoriteSmoke;

  @Column(name = "favorite_smoke_important")
  private Boolean favoriteSmokeImportant;

  @Column(name = "favorite_drink")
  private String favoriteDrink;

  @Column(name = "favorite_drink_important")
  private Boolean favoriteDrinkImportant;

  @Column(name = "favorite_faith")
  private String favoriteFaith;

  @Column(name = "favorite_faith_important")
  private Boolean favoriteFaithImportant;

  @Column(name = "favorite_location")
  private String favoriteLocation;

  @Column(name = "favorite_location_important")
  private Boolean favoriteLocationImportant;

  public static BlindDate of(Integer season, Account account) {
    return builder()
      // =========== 메타 정보 ===========
      .matchingBlindDateId(null)
      .isActive(false)
      .isMatched(false)
      .season(season)
      .isReveal(false)
      .isSubmitted(false)
      .isRetry(false)
      .account(account)
      .build();
  }

  public void updateMatchingBlindDate(Long matchingId) {
    matchingBlindDateId = matchingId;
  }

  public void updateMatchingApproval(Boolean approval) {
    isActive = true;
    isMatched = approval;
  }

  public void updateBlindDate(BlindDateRequestDTO request) {
    // =========== 내 정보 ===========
    this.name = request.getName();
    this.gender = request.getGender();
    this.myAge = request.getMyAge();
    this.myHeight = request.getMyHeight();
    this.myDrink = request.getMyDrink();
    this.myLocation = request.getMyLocation();
    this.myLocationDesc = request.getMyLocationDesc();
    this.hobby = request.getHobby();
    this.hobbyDesc = request.getHobbyDesc();
    this.myContactStyle = request.getMyContactStyle();
    this.myCharacter = request.getMyCharacter();
    this.dateStyle = request.getDateStyle();
    this.faith = request.getFaith();
    this.mbti = request.getMbti();
    this.smoke = request.getSmoke();
    this.comment = request.getComment();
    this.kakaoLink = request.getKakaoLink();

    // =========== 선호하는 상대방 정보 ===========
    this.favoriteAge = request.getFavoriteAge();
    this.favoriteHeight = request.getFavoriteHeight();
    this.favoriteSmoke = request.getFavoriteSmoke();
    this.favoriteSmokeImportant = request.getFavoriteSmokeImportant();
    this.favoriteDrink = request.getFavoriteDrink();
    this.favoriteDrinkImportant = request.getFavoriteDrinkImportant();
    this.favoriteFaith = request.getFavoriteFaith();
    this.favoriteFaithImportant = request.getFavoriteFaithImportant();
    this.favoriteLocation = request.getFavoriteLocation();
    this.favoriteLocationImportant = request.getFavoriteLocationImportant();
  }
}

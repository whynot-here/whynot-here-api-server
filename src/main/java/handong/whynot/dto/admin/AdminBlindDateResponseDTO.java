package handong.whynot.dto.admin;

import handong.whynot.domain.BlindDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminBlindDateResponseDTO {

  // =========== 메타 정보 ===========
  private Long blindDateId;
  private Boolean matchedByAdmin;
  private Boolean isSubmitted;
  private Boolean isRetry;

  // =========== 내 정보 ===========
  private String name;
  private String gender;
  private Integer myAge;
  private Integer myHeight;
  private String myDrink;
  private String myLocation;
  private String myLocationDesc;
  private String hobby;
  private String hobbyDesc;
  private String myContactStyle;
  private String myCharacter;
  private String dateStyle;
  private String faith;
  private String mbti;
  private String smoke;
  private String comment;
  private String kakaoLink;
  private String retryReason;
  private String mannersReason;
  private String mannersReasonDesc;
  // =========== 선호하는 상대방 정보 ===========
  private String favoriteAge;
  private String favoriteHeight;
  private String favoriteSmoke;
  private Boolean favoriteSmokeImportant;
  private String favoriteDrink;
  private Boolean favoriteDrinkImportant;
  private String favoriteFaith;
  private Boolean favoriteFaithImportant;
  private String favoriteLocation;
  private Boolean favoriteLocationImportant;

  public static AdminBlindDateResponseDTO of(BlindDate blindDate) {
    return builder()
      // =========== 메타 정보 ===========
      .blindDateId(blindDate.getId())
      .matchedByAdmin(blindDate.getMatchingBlindDateId() != null)
      .isSubmitted(blindDate.getIsSubmitted())
      .isRetry(blindDate.getIsRetry())
      // =========== 내 정보 ===========
      .name(blindDate.getName())
      .gender(blindDate.getGender())
      .myAge(blindDate.getMyAge())
      .myHeight(blindDate.getMyHeight())
      .myDrink(blindDate.getMyDrink())
      .myLocation(blindDate.getMyLocation())
      .myLocationDesc(blindDate.getMyLocationDesc())
      .hobby(blindDate.getHobby())
      .hobbyDesc(blindDate.getHobbyDesc())
      .myContactStyle(blindDate.getMyContactStyle())
      .myCharacter(blindDate.getMyCharacter())
      .dateStyle(blindDate.getDateStyle())
      .faith(blindDate.getFaith())
      .mbti(blindDate.getMbti())
      .smoke(blindDate.getSmoke())
      .comment(blindDate.getComment())
      .kakaoLink(blindDate.getKakaoLink())
      .retryReason(blindDate.getRetryReason())
      .mannersReason(blindDate.getMannersReason())
      .mannersReasonDesc(blindDate.getMannersReasonDesc())
      // =========== 선호하는 상대방 정보 ===========
      .favoriteAge(blindDate.getFavoriteAge())
      .favoriteHeight(blindDate.getFavoriteHeight())
      .favoriteSmoke(blindDate.getFavoriteSmoke())
      .favoriteSmokeImportant(blindDate.getFavoriteSmokeImportant())
      .favoriteDrink(blindDate.getFavoriteDrink())
      .favoriteDrinkImportant(blindDate.getFavoriteDrinkImportant())
      .favoriteFaith(blindDate.getFavoriteFaith())
      .favoriteFaithImportant(blindDate.getFavoriteFaithImportant())
      .favoriteLocation(blindDate.getFavoriteLocation())
      .favoriteLocationImportant(blindDate.getFavoriteLocationImportant())
      .build();
  }
}

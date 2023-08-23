package handong.whynot.dto.admin;

import handong.whynot.domain.BlindDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminBlindDateResponseDTO {

  private Long blindDateId;
  private Boolean matchedByAdmin;
  private String name;
  private String gender;
  private Integer myAge;
  private String favoriteAge;
  private String dateStyle;
  private String hobby;
  private String faith;
  private String mbti;
  private String smoke;
  private String comment;
  private String kakaoLink;

  public static AdminBlindDateResponseDTO of(BlindDate blindDate) {
    return builder()
      .blindDateId(blindDate.getId())
      .name(blindDate.getName())
      .gender(blindDate.getGender())
      .myAge(blindDate.getMyAge())
      .favoriteAge(blindDate.getFavoriteAge())
      .dateStyle(blindDate.getDateStyle())
      .hobby(blindDate.getHobby())
      .faith(blindDate.getFaith())
      .mbti(blindDate.getMbti())
      .smoke(blindDate.getSmoke())
      .comment(blindDate.getComment())
      .kakaoLink(blindDate.getKakaoLink())
      .matchedByAdmin(blindDate.getMatchingBlindDateId() != null)
      .build();
  }
}

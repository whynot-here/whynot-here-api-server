package handong.whynot.dto.blind_date;

import handong.whynot.domain.BlindDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlindDateResponseDTO {

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

  public static BlindDateResponseDTO of(BlindDate blindDate) {
    return builder()
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
      .build();
  }
}

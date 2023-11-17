package handong.whynot.dto.blind_date;

import handong.whynot.domain.BlindDate;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlindDateMatchingResponseDTO {

  private String name;
  private String gender;
  private Integer myAge;
  private String favoriteAge;
  private String dateStyle;
  private String hobby;
  private String faith;
  private String mbti;
  private String smoke;
  private String commentForMate;
  private String kakaoLink;
  private String profileImg;
  private String myName;

  public static BlindDateMatchingResponseDTO of(BlindDate blindDate, String profileImg, String myName) {
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
      .commentForMate(blindDate.getCommentForMate())
      .kakaoLink(blindDate.getKakaoLink())
      .profileImg(profileImg)
      .myName(myName)
      .build();
  }
}

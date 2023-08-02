package handong.whynot.dto.blind_date;

import handong.whynot.domain.ExcludeCond;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlindDateRequestDTO {

  private Integer season;
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
  private List<ExcludeCond> excludeCondList;
}

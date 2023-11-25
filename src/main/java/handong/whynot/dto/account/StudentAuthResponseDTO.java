package handong.whynot.dto.account;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentAuthResponseDTO {
  private Long accountId;
  private String imgUrl;
  private String backImgUrl;
  private boolean isAuthenticated;
}

package handong.whynot.dto.account.oauth2;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AppleTokenResponseDTO {

  private String idToken;

  private String accessToken;

  private String refreshToken;

  private String expiresIn;

  private String tokenType;
}

package handong.whynot.dto.account.oauth2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown =true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApplePayloadDTO {

  private String iss;

  private List<String> aud;

  private Date exp;

  private Date iat;

  private String sub;

  private String nonce;

  private String email;

  private String emailVerified;

  private Long authTime;

  private boolean nonceSupported;
}

package handong.whynot.dto.account.oauth2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppleTokenRequestDTO {

  @feign.form.FormProperty("client_id")
  private String clientId;

  @feign.form.FormProperty("client_secret")
  private String clientSecret;

  @feign.form.FormProperty("code")
  private String code;

  @feign.form.FormProperty("grant_type")
  private String grantType;

  @feign.form.FormProperty("redirect_uri")
  private String redirectUri;
}

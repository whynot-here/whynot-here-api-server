package handong.whynot.dto.account.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.apple")
public class OAuth2AppleConst {

  private String issuer;
  private String audience;
  private String teamId;
  private String keyId;
  private String keyPath;
  private String redirectUrl;
  private String authorizationGrantType;
}

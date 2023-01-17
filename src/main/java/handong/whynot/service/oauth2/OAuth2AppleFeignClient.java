package handong.whynot.service.oauth2;

import handong.whynot.dto.account.oauth2.AppleTokenRequestDTO;
import handong.whynot.dto.account.oauth2.AppleTokenResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "oAuth2AppleFeignClient", url = "https://appleid.apple.com/auth")
public interface OAuth2AppleFeignClient {

  @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  AppleTokenResponseDTO validateAndGetAppleOAuthToken(AppleTokenRequestDTO request);
}


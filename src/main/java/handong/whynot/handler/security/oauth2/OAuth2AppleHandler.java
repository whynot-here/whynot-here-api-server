package handong.whynot.handler.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.ECDSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import handong.whynot.domain.AuthType;
import handong.whynot.dto.account.oauth2.*;
import handong.whynot.exception.account.FailToCreateAppleOauth2ClientSecretException;
import handong.whynot.exception.account.FailToReadAppleOauth2PrivateKeyException;
import handong.whynot.service.oauth2.CustomOAuth2UserService;
import handong.whynot.service.oauth2.OAuth2AppleFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

import static handong.whynot.dto.account.AccountResponseCode.ACCOUNT_OAUTH2_PROCESS_FAILED;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(OAuth2AppleConst.class)
public class OAuth2AppleHandler {

  private final ObjectMapper objectMapper;
  private final OAuth2AppleConst oAuth2AppleConst;
  private final OAuth2AppleFeignClient oAuth2AppleFeignClient;
  private final CustomOAuth2UserService customOAuth2UserService;
  private final OAuth2FailureHandler oAuth2FailureHandler;
  private final OAuth2SuccessHandler oAuth2SuccessHandler;
  private final ResourceLoader resourceLoader;

  private final static String APPLE_NAME_ATTRIBUTE = "nickname";

  /**
   * Code 유효성 검증 및 Apple OAuth 성공 처리
   *
   * @param request
   * @param response
   * @param serviceResponse
   * @throws Exception
   */
  public void validateRequestCodeAndDoLogin(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AppleServicesResponseDTO serviceResponse) throws Exception {

    final String code = serviceResponse.getCode();
    final String clientSecret = createClientSecret();

    // 유효 값 검증
    if (StringUtils.isEmpty(code) || StringUtils.isEmpty(clientSecret)) {
      OAuth2Error oauth2Error = new OAuth2Error(
        ACCOUNT_OAUTH2_PROCESS_FAILED.getMessage(),
        ACCOUNT_OAUTH2_PROCESS_FAILED.getMessage(),
        null
      );
      oAuth2FailureHandler.onAuthenticationFailure(request, response, new OAuth2AuthenticationException(oauth2Error));
      return;
    }

    final AppleTokenResponseDTO tokenResponse = validateAuthorizationGrantCode(clientSecret, code);
    final String idToken = tokenResponse.getIdToken();
    final SignedJWT signedJWT = SignedJWT.parse(idToken);
    final JWTClaimsSet getPayload = signedJWT.getJWTClaimsSet();
    final ApplePayloadDTO applePayloadDTO = objectMapper.convertValue(getPayload.getClaims(), ApplePayloadDTO.class);

    final Map<String, Object> attributes = new HashMap<>();
    attributes.put(APPLE_NAME_ATTRIBUTE, applePayloadDTO.getEmail().split("@")[0]);
    attributes.put("email", applePayloadDTO.getEmail());
    attributes.put("nameAttributeKey", APPLE_NAME_ATTRIBUTE);
    attributes.put("authType", AuthType.apple);
    attributes.put("sub", applePayloadDTO.getSub());

    final DefaultOAuth2User defaultOAuth2User = new DefaultOAuth2User(
      List.of(),
      attributes,
      APPLE_NAME_ATTRIBUTE
    );

    final Authentication authentication = new OAuth2AuthenticationToken(
      customOAuth2UserService.processOAuth2UserForApple(defaultOAuth2User),
      List.of(),
      AuthType.apple.toString()
    );

    oAuth2SuccessHandler.onAuthenticationSuccess(request, response, authentication);
  }

  /**
   * client_secret 생성
   *
   * @return clientSecret(jwt)
   */
  private String createClientSecret() {

    final JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.ES256).keyID(oAuth2AppleConst.getKeyId()).build();
    final Date now = new Date();

    final JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
      .subject(oAuth2AppleConst.getAudience())
      .audience(oAuth2AppleConst.getIssuer())
      .issuer(oAuth2AppleConst.getTeamId())
      .expirationTime(new Date(now.getTime() + 60 * 60 * 1000))
      .issueTime(now)
      .build();

    final SignedJWT jwt = new SignedJWT(header, claimsSet);

    try {
      final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(readPrivateKey());
      final KeyFactory kf = KeyFactory.getInstance("EC");
      final ECPrivateKey decoded = (ECPrivateKey) kf.generatePrivate(spec);
      final JWSSigner jwsSigner = new ECDSASigner(decoded);

      jwt.sign(jwsSigner);
      return jwt.serialize();
    } catch (Exception ex) {
      log.error("client secret 생성에 실패했습니다. ", ex);
      throw new FailToCreateAppleOauth2ClientSecretException(ACCOUNT_OAUTH2_PROCESS_FAILED);
    }
  }

  /**
   * 파일에서 private key 획득
   *
   * @return privateKey
   */
  private byte[] readPrivateKey() {

    try {
      InputStream resourceAsStream = resourceLoader.getClass().getResourceAsStream(oAuth2AppleConst.getKeyPath());
      PemReader pemReader;
      if (Objects.isNull(resourceAsStream)) {
        FileReader keyReader = new FileReader(oAuth2AppleConst.getKeyPath());
        pemReader = new PemReader(keyReader);
      } else {
        Reader keyReader = new InputStreamReader(resourceAsStream);
        pemReader = new PemReader(keyReader);
      }

      PemObject pemObject = pemReader.readPemObject();
      return pemObject.getContent();
    } catch (IOException ex) {
      log.error("readPrivateKey private key 읽기에 실패하였습니다. ", ex);
      throw new FailToReadAppleOauth2PrivateKeyException(ACCOUNT_OAUTH2_PROCESS_FAILED);
    }
  }

  /**
   * 유효한 code 인지 Apple Server에 확인 요청
   *
   * @param clientSecret
   * @param code
   * @return
   */
  private AppleTokenResponseDTO validateAuthorizationGrantCode(final String clientSecret, final String code) {

    AppleTokenRequestDTO request = new AppleTokenRequestDTO();

    request.setClientId(oAuth2AppleConst.getAudience());
    request.setGrantType(oAuth2AppleConst.getAuthorizationGrantType());
    request.setRedirectUri(oAuth2AppleConst.getRedirectUrl());
    request.setClientSecret(clientSecret);
    request.setCode(code);

    return oAuth2AppleFeignClient.validateAndGetAppleOAuthToken(request);
  }
}

package handong.whynot.service.oauth2;

import handong.whynot.domain.AuthType;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.oauth2.*;
import handong.whynot.exception.account.OAuth2NotProvidedException;
import handong.whynot.exception.account.OAuth2ProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthType.google.toString())) {
            SecuredOAuth2DTO securedOAuth2DTO = getGoogleOAuth2User(attributes);
            return new GoogleOAuth2UserInfo(securedOAuth2DTO);
        }
        else if (registrationId.equalsIgnoreCase(AuthType.kakao.toString())) {
            SecuredOAuth2DTO securedOAuth2DTO = getKakaoOAuth2User(attributes);
            return new KakaoOAuth2UserInfo(securedOAuth2DTO);
        }
        else if (registrationId.equalsIgnoreCase(AuthType.naver.toString())) {
            SecuredOAuth2DTO securedOAuth2DTO = getNaverOAuth2User(attributes);
            return new NaverOAuth2UserInfo(securedOAuth2DTO);
        } else {
            throw new OAuth2ProcessingException(AccountResponseCode.ACCOUNT_OAUTH2_PROCESS_FAILED);
        }
    }

    private static SecuredOAuth2DTO getGoogleOAuth2User(Map<String, Object> attributes) {

        try {
            String id = (String) attributes.get("sub");
            String name = (String) attributes.get("name");
            String email = (String) attributes.get("email");
            String profileImg = (String) attributes.get("picture");

            return SecuredOAuth2DTO.builder()
                    .id(id)
                    .name(name)
                    .email(email)
                    .profileImg(profileImg)
                    .build();
        } catch (Exception e) {
            // 획득하지 못한 정보가 있을 때
            throw new OAuth2NotProvidedException(AccountResponseCode.ACCOUNT_OAUTH2_NOT_PROVIDED_VALUE);
        }

    }

    private static SecuredOAuth2DTO getKakaoOAuth2User(Map<String, Object> attributes) {

        try {
            Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
            Map<String, Object> profileMap = (Map<String, Object>) kakao_account.get("profile");

            String id = ((Long) attributes.get("id")).toString();
            String name = (String) profileMap.get("nickname");
            String email = (String) kakao_account.get("email");
            String profileImg = (String) profileMap.get("profile_image_url");

            return SecuredOAuth2DTO.builder()
                    .id(id)
                    .name(name)
                    .email(email)
                    .profileImg(profileImg)
                    .build();
        } catch (Exception e) {
            // 획득하지 못한 정보가 있을 때
            throw new OAuth2NotProvidedException(AccountResponseCode.ACCOUNT_OAUTH2_NOT_PROVIDED_VALUE);
        }
    }

    private static SecuredOAuth2DTO getNaverOAuth2User(Map<String, Object> attributes) {

        try {
            final Map<String, Object> response = (Map<String, Object>) attributes.get("response");

            String id = (String) response.get("id");
            String name = (String) response.get("name");
            String email = (String) response.get("email");
            String profileImg = (String) response.get("profile_image");

            return SecuredOAuth2DTO.builder()
                    .id(id)
                    .name(name)
                    .email(email)
                    .profileImg(profileImg)
                    .build();
        } catch (Exception e) {
            // 획득하지 못한 정보가 있을 때
            throw new OAuth2NotProvidedException(AccountResponseCode.ACCOUNT_OAUTH2_NOT_PROVIDED_VALUE);
        }
    }

    private static Boolean IsNotProvidedUserInfo(String id, String name, String email, String profileImg) {
        return id == null || name == null || email == null || profileImg == null;
    }
}
package handong.whynot.service.oauth2;

import handong.whynot.domain.AuthType;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.oauth2.*;
import handong.whynot.exception.account.OAuth2ProcessingException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase(AuthType.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        }
        else {
            throw new OAuth2ProcessingException(AccountResponseCode.ACCOUNT_OAUTH2_PROCESS_FAILED);
        }
    }
}
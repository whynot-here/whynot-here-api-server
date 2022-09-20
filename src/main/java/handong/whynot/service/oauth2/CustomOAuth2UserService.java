package handong.whynot.service.oauth2;

import handong.whynot.domain.Account;
import handong.whynot.domain.AuthType;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.UserAccount;
import handong.whynot.dto.account.oauth2.OAuth2UserInfo;
import handong.whynot.dto.common.ResponseCode;
import handong.whynot.exception.AbstractBaseException;
import handong.whynot.exception.account.OAuth2ExistEmailException;
import handong.whynot.exception.account.OAuth2ProcessingException;
import handong.whynot.repository.AccountRepository;
import handong.whynot.service.CategoryService;
import handong.whynot.util.NicknameMaker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AccountRepository accountRepository;
    private final CategoryService categoryService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception ex) {
            ResponseCode code = ((AbstractBaseException) ex).getResponseCode();

            OAuth2Error oauth2Error = new OAuth2Error(
                    code.getStatusCode().toString(),
                    code.getMessage(),
                    userRequest.getClientRegistration().getRegistrationId()
            );

            throw new OAuth2AuthenticationException(oauth2Error);
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();

        // 제공하는 소셜 로그인 registration(구글, 카카오, 네이버) 인지 확인
        if (!AuthType.isValidRegistrationId(registrationId)) {
            throw new OAuth2ProcessingException(AccountResponseCode.ACCOUNT_OAUTH2_INVALID_REGISTRATION);
        }

        // OAuth2 사용자 필수 정보 획득
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());

        // 기존 사용자인지 확인
        Account account = accountRepository.findByOauthCI(oAuth2UserInfo.getId());

        if (account == null) {
            account = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        } else {
            if (!StringUtils.equals(registrationId, account.getAuthType().toString())) {
                throw new OAuth2ExistEmailException(AccountResponseCode.ACCOUNT_OAUTH2_EXIST_SAME_EMAIL);
            }
        }

        return new UserAccount(account);
    }

    private Account registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        Account account = Account.builder()
                .nickname(NicknameMaker.make(oAuth2UserInfo.getName()))
                .email(oAuth2UserInfo.getEmail())
                .profileImg(oAuth2UserInfo.getProfileImg())
                .authType(AuthType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .emailVerified(true)
                .joinedAt(LocalDateTime.now())
                .oauthCI(oAuth2UserInfo.getId())
                .categoryOrder(categoryService.initOrder())
                .build();

        return accountRepository.save(account);
    }
}

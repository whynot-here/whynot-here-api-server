package handong.whynot.service.oauth2;

import handong.whynot.domain.Account;
import handong.whynot.domain.AuthType;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.account.UserAccount;
import handong.whynot.dto.account.oauth2.OAuth2UserInfo;
import handong.whynot.exception.account.OAuth2ProcessingException;
import handong.whynot.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AccountRepository accountRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        if (! AuthType.isValidRegistrationId(registrationId)) {
            throw new OAuth2ProcessingException(AccountResponseCode.ACCOUNT_OAUTH2_INVALID_REGISTRATION);
        }

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2ProcessingException(AccountResponseCode.ACCOUNT_OAUTH2_PROCESS_FAILED);
        }

        Account findAccount = accountRepository.findByEmail(oAuth2UserInfo.getEmail());
        Account account;

        if (findAccount != null) {
            account = updateExistingUser(findAccount, oAuth2UserInfo);
        } else {
            account = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserAccount.create(account, oAuth2User.getAttributes());
    }

    private Account registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        Account account = Account.builder()
                            .nickname(oAuth2UserInfo.getName())
                            .email(oAuth2UserInfo.getEmail())
                            .profileImg(oAuth2UserInfo.getProfileImg())
                            .authType(AuthType.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                            .emailVerified(true)
                            .joinedAt(LocalDateTime.now())
                            .build();

        return accountRepository.save(account);
    }

    private Account updateExistingUser(Account existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setNickname(oAuth2UserInfo.getName());
        existingUser.setProfileImg(oAuth2UserInfo.getProfileImg());
        return accountRepository.save(existingUser);
    }
}

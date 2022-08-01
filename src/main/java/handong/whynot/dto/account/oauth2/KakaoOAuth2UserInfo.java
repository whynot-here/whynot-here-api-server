package handong.whynot.dto.account.oauth2;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(SecuredOAuth2DTO dto) {
        super(dto);
    }

    @Override
    public String getId() {
        return super.id;
    }

    @Override
    public String getName() {
        return super.name;
    }

    @Override
    public String getEmail() {
        return super.email;
    }

    @Override
    public String getProfileImg() {
        return super.profileImg;
    }
}

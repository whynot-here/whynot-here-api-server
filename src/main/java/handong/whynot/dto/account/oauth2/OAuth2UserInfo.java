package handong.whynot.dto.account.oauth2;

public abstract class OAuth2UserInfo {

    protected String id;
    protected String name;
    protected String email;
    protected String profileImg;

    protected OAuth2UserInfo(SecuredOAuth2DTO dto) {
        id = dto.getId();
        name = dto.getName();
        email = dto.getEmail();
        profileImg = dto.getProfileImg();
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getProfileImg();
}

package handong.whynot.dto.account.oauth2;

public abstract class OAuth2UserInfo {

    protected String id;
    protected String name;
    protected String email;
    protected String profileImg;

    public OAuth2UserInfo(SecuredOAuth2DTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.profileImg = dto.getProfileImg();
    }

    public abstract String getId();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getProfileImg();
}

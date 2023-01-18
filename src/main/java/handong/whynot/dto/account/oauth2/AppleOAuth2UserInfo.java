package handong.whynot.dto.account.oauth2;

import lombok.Getter;

@Getter
public class AppleOAuth2UserInfo extends OAuth2UserInfo {

  public AppleOAuth2UserInfo(SecuredOAuth2DTO dto) {
    super(dto);
  }

  @Override
  public String getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public String getProfileImg() {
    return profileImg;
  }
}

package handong.whynot.dto.account.oauth2;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecuredOAuth2DTO {

    private String id;
    private String name;
    private String email;
    private String profileImg;
}

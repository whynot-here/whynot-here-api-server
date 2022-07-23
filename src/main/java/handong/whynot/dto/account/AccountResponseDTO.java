package handong.whynot.dto.account;

import handong.whynot.domain.Account;
import handong.whynot.domain.AuthType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDTO {

    private Long id;
    private String email;
    private String nickname;
    private String profileImg;
    private AuthType authType;

    public static AccountResponseDTO of(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .email(account.getEmail())
                .nickname(account.getNickname())
                .profileImg(account.getProfileImg())
                .authType(account.getAuthType())
                .build();
    }
}

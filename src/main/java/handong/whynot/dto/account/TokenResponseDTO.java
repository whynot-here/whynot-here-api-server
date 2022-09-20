package handong.whynot.dto.account;

import handong.whynot.domain.Account;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDTO {

    private String accessToken;

    private String refreshToken;

    private AccountResponseDTO accountResponseDTO;

    public static TokenResponseDTO of(Account account, String accessToken, String refreshToken) {
        return builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accountResponseDTO(AccountResponseDTO.of(account))
                .build();
    }
}

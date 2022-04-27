package handong.whynot.dto.account;

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
}

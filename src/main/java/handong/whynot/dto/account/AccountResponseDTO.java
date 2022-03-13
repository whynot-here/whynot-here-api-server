package handong.whynot.dto.account;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountResponseDTO {

    private Long id;
    private String email;
    private String nickname;
    private String profileImg;
}

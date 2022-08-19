package handong.whynot.dto.post;

import handong.whynot.domain.Account;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostWriterDTO {

    private String email;
    private String nickname;
    private String profileImg;

    public static PostWriterDTO of(Account account) {
        return PostWriterDTO.builder()
                .email(account.getEmail())
                .nickname(account.getNickname())
                .profileImg(account.getProfileImg())
                .build();
    }
}

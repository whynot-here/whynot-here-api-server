package handong.whynot.domain;

import handong.whynot.dto.account.AccountResponseDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "email_verified")
    private Boolean emailVerified;

    @Column(name = "email_check_token")
    private String emailCheckToken;

    @Column(name = "email_check_token_generated_at")
    private LocalDateTime emailCheckTokenGeneratedAt;

    @Column(name = "auth_type")
    @Enumerated(EnumType.STRING)
    private AuthType authType;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

    public void generateEmailCheckToken() {
        emailCheckToken = UUID.randomUUID().toString().split("-")[0];
        emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void completeSignUp() {
        emailVerified = true;
        joinedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return emailCheckToken.equals(token);
    }

    public AccountResponseDTO getAccountDTO() {
        return AccountResponseDTO.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .profileImg(profileImg)
                .build();
    }
}

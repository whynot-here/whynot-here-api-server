package handong.whynot.dto.account;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class TokenCheckRequestDTO {

    @NotBlank
    private String token;

    @Email
    @NotBlank
    private String email;

}

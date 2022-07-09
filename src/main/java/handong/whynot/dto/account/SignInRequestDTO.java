package handong.whynot.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class SignInRequestDTO {

    @NotBlank // null 과 "" 과 " " 모두 허용X
    private String email;

    @NotBlank
    private String password;
}

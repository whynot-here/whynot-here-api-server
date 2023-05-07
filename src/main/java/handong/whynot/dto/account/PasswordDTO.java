package handong.whynot.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {

  @NotBlank
  private String currentPassword;

  @NotBlank
  private String newPassword;
}

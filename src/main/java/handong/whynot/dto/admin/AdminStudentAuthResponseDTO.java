package handong.whynot.dto.admin;

import handong.whynot.domain.StudentAuth;
import handong.whynot.dto.account.AccountResponseDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminStudentAuthResponseDTO {
  private Long id;
  private AccountResponseDTO account;
  private String imgUrl;
  private boolean isAuthenticated;

  public static AdminStudentAuthResponseDTO of(StudentAuth auth) {
    return AdminStudentAuthResponseDTO.builder()
      .id(auth.getId())
      .account(AccountResponseDTO.of(auth.getAccount()))
      .imgUrl(auth.getImgUrl())
      .isAuthenticated(auth.isAuthenticated())
      .build();
  }
}

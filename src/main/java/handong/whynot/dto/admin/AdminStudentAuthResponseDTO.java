package handong.whynot.dto.admin;

import handong.whynot.domain.Account;
import handong.whynot.domain.AuthType;
import handong.whynot.domain.StudentAuth;
import handong.whynot.dto.account.AccountResponseDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminStudentAuthResponseDTO {
  private Long id;
  private Long accountId;
  private String email;
  private String nickname;
  private String profileImg;
  private AuthType authType;
  private Integer studentId;
  private String studentName;
  private String imgUrl;
  private boolean isAuthenticated;

  public static AdminStudentAuthResponseDTO of(StudentAuth auth) {
    Account account = auth.getAccount();
    return AdminStudentAuthResponseDTO.builder()
      .id(auth.getId())
      .accountId(account.getId())
      .email(account.getEmail())
      .nickname(account.getNickname())
      .profileImg(account.getProfileImg())
      .authType(account.getAuthType())
      .studentId(account.getStudentId())
      .studentName(account.getStudentName())
      .imgUrl(auth.getImgUrl())
      .isAuthenticated(auth.isAuthenticated())
      .build();
  }
}

package handong.whynot.dto.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import handong.whynot.domain.Account;
import handong.whynot.domain.AuthType;
import handong.whynot.domain.StudentAuth;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminStudentAuthResponseDTO {
  private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

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

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createdDt;

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
      .isAuthenticated(auth.getIsAuthenticated())
      .createdDt(auth.getCreatedDt())
      .build();
  }
}

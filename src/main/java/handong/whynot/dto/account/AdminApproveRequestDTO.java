package handong.whynot.dto.account;

import lombok.Getter;

@Getter
public class AdminApproveRequestDTO {
  private Long accountId;
  private Integer studentId;
  private String studentName;
}

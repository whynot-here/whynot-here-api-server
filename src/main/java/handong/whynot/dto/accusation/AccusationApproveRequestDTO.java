package handong.whynot.dto.accusation;

import lombok.Getter;

@Getter
public class AccusationApproveRequestDTO {
  private Long accusationId;
  private Boolean approval;
}

package handong.whynot.dto.blind_date;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlindDateStepResponseDTO {
  private Integer myStep;
  private Integer favoriteStep;
}

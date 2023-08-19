package handong.whynot.dto.accusation;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccusationRequestDTO {
  private Long postId;
  private String reason;
}

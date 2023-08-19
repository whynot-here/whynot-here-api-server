package handong.whynot.dto.accusation;

import handong.whynot.domain.Accusation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccusationResponseDTO {
  private Long accusationId;
  private String reporterName;
  private String reason;
  private String postTitle;
  private String postContent;
  private String postWriterName;
  private LocalDateTime createdDt;
  private Boolean isApproved;

  public static AccusationResponseDTO of(Accusation accusation) {
    return AccusationResponseDTO.builder()
      .accusationId(accusation.getId())
      .reporterName(accusation.getReporter().getNickname())
      .reason(accusation.getReason())
      .postWriterName(accusation.getPost().getCreatedBy().getNickname())
      .postTitle(accusation.getPost().getTitle())
      .postContent(accusation.getPost().getContent())
      .createdDt(accusation.getCreatedDt())
      .isApproved(accusation.getIsApproved())
      .build();
  }
}

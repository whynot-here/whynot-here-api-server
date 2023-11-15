package handong.whynot.dto.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import handong.whynot.domain.MatchingHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class AdminBlindMatchingResponseDTO {

  private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

  private Long historyId;
  private Integer season;
  private Long maleId;
  private Long femaleId;
  private String maleImageLink;
  private String femaleImageLink;
  private Boolean isApproved;
  private String approver;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createdDt;

  public static AdminBlindMatchingResponseDTO of(MatchingHistory history) {
    return builder()
      .historyId(history.getId())
      .season(history.getSeason())
      .maleId(history.getMaleId())
      .femaleId(history.getFemaleId())
      .maleImageLink(history.getMaleImageLink())
      .femaleImageLink(history.getFemaleImageLink())
      .isApproved(history.getIsApproved())
      .approver(history.getApprover())
      .createdDt(history.getCreatedDt())
      .build();
  }
}

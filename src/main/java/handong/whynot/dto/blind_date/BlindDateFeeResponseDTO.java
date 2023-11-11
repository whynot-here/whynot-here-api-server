package handong.whynot.dto.blind_date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import handong.whynot.domain.BlindDateFee;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlindDateFeeResponseDTO {

  private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

  private Long id;
  private Long accountId;
  private String name;
  private String bankName;
  private String bankNumber;
  private Integer season;
  private Boolean isSubmitted;
  private String useYn;
  private String approver;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime createdDt;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  private LocalDateTime updatedDt;

  public static BlindDateFeeResponseDTO of(BlindDateFee fee) {
    return builder()
      .id(fee.getId())
      .accountId(fee.getAccountId())
      .name(fee.getName())
      .bankName(fee.getBankName())
      .bankNumber(fee.getBankNumber())
      .season(fee.getSeason())
      .isSubmitted(fee.getIsSubmitted())
      .useYn(fee.getUseYn())
      .createdDt(fee.getCreatedDt())
      .updatedDt(fee.getUpdatedDt())
      .approver(fee.getApprover())
      .build();
  }
}

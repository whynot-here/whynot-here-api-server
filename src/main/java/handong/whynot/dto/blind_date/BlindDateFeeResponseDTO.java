package handong.whynot.dto.blind_date;

import handong.whynot.domain.BlindDateFee;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlindDateFeeResponseDTO {

  private Long id;
  private Long accountId;
  private String name;
  private String bankName;
  private String bankNumber;
  private Integer season;
  private Boolean isSubmitted;
  private String useYn;

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
      .build();
  }
}

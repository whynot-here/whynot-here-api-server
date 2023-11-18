package handong.whynot.domain;

import handong.whynot.domain.common.BaseTimeEntity;
import handong.whynot.dto.blind_date.BlindDateFeeRequestDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class BlindDateFee extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "account_id")
  private Long accountId;

  @Column(name = "name")
  private String name;

  @Column(name = "bank_name")
  private String bankName;

  @Column(name = "bank_number")
  private String bankNumber;

  @Column(name = "season")
  private Integer season;

  @Column(name = "is_submitted")
  private Boolean isSubmitted = false;

  @Column(name = "use_yn")
  private String useYn;

  @Column(name = "approver")
  private String approver;

  public static BlindDateFee of(Long accountId, BlindDateFeeRequestDTO dto) {
    return builder()
      .accountId(accountId)
      .name(dto.getName())
      .bankName(dto.getBankName())
      .bankNumber(dto.getBankNumber())
      .season(dto.getSeason())
      .isSubmitted(false)
      .useYn("Y")
      .build();
  }

  public void deleteBlindDateFee() {
    useYn = "N";
  }

  public void approveBlindDateFee(String email) {
    isSubmitted = true;
    approver = email;
  }
}

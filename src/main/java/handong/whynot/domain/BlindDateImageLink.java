package handong.whynot.domain;

import handong.whynot.domain.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class BlindDateImageLink extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "account_id")
  private Long accountId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "blind_date_id")
  private BlindDate blindDate;

  @Column(name = "link")
  private String link;

  public static BlindDateImageLink of(Long accountId, BlindDate blindDate, String link) {
    return builder()
      .accountId(accountId)
      .blindDate(blindDate)
      .link(link)
      .build();
  }
}

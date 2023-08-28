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
public class BlockAccount extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "account_id")
  private Long accountId;

  @Column(name = "block_account_id")
  private Long blockAccountId;
}

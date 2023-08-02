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
public class MatchingHistory extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "blind_date_id")
  private Long blindDateId;

  @Column(name = "matching_blind_date_id")
  private Long matchingBlindDateId;
}

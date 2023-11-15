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

  @Column(name = "male_id")
  private Long maleId;

  @Column(name = "female_id")
  private Long femaleId;

  @Column(name = "season")
  private Integer season;

  @Column(name = "male_image_link")
  private String maleImageLink;

  @Column(name = "female_image_link")
  private String femaleImageLink;

  @Column(name = "is_approved")
  private Boolean isApproved;

  @Column(name = "approver")
  private String approver;

  @Column(name = "is_retry")
  private Boolean isRetry;
}

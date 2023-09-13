package handong.whynot.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class BlindDateSummary {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "totalCnt")
  private Integer totalCnt;

  @Column(name = "maleCnt")
  private Integer maleCnt;

  @Column(name = "femaleCnt")
  private Integer femaleCnt;

  @Column(name = "matchedCnt")
  private Integer matchedCnt;

  @Column(name = "season")
  private Integer season;
}

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
public class ExcludeCond extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "blind_date_id")
  private BlindDate blindDate;

  @Column(name = "name")
  private String name;

  @Column(name = "department")
  private String department;

  @Column(name = "student_id")
  private String studentId;
}

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
public class FriendMatchingHistory extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "friend_id1")
  private Long friendId1;

  @Column(name = "friend_id2")
  private Long friendId2;

  @Column(name = "season")
  private Integer season;
}

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
@Table(
        name = "job_post",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "constJobPost",
                        columnNames = {"job_id", "post_id"}
                )
        }
)
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}

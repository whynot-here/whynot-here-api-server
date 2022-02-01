package handong.whynot.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@EqualsAndHashCode(of = "id")
public class JobPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    public Job job;

    @ManyToOne
    @JoinColumn(name = "post_id")
    public Post post;

}

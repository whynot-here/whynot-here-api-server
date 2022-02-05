package handong.whynot.domain;

import handong.whynot.domain.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "post")
    private List<JobPost> jobPosts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account createdBy;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "post_img")
    private String postImg;

    @Column(name = "is_recruiting")
    private boolean isRecruiting;

    @Column(name = "closed_dt")
    private LocalDateTime closedDt;

    @Builder
    public Post(Account createdBy, String title, String content, String postImg) {
        this.createdBy = createdBy;
        this.title = title;
        this.content = content;
        this.postImg = postImg;
        this.isRecruiting = true;
    }

    public void addJobs(List<JobPost> jobPosts) {
        this.jobPosts = jobPosts;
    }
}

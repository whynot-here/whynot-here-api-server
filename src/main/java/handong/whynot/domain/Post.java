package handong.whynot.domain;

import handong.whynot.domain.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(of = "id")
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @OneToMany(mappedBy = "post")
    private List<JobPost> jobPosts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User createdBy;

    @Column(name = "title")
    public String title;

    @Column(name = "content")
    public String content;

    @Column(name = "post_img")
    public String postImg;

    @Column(name = "is_recruiting")
    public boolean isRecruiting;

    @Column(name = "closed_dt")
    public LocalDateTime closedDt;

    @Builder
    public Post(User createdBy, String title, String content, String postImg) {
        this.createdBy = createdBy;
        this.title = title;
        this.content = content;
        this.postImg = postImg;
    }

    public Post addJobs(List<JobPost> jobs) {
        this.jobPosts = jobPosts;
        return this;
    }
}

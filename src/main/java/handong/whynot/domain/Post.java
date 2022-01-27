package handong.whynot.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @OneToMany(mappedBy = "post")
    private List<JobPost> jobPosts = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User createdBy;

//    @OneToMany
//    public List<User> applicants = new ArrayList<>();

    @Column(name = "title")
    public String title;

    @Column(name = "content")
    public String content;

    @Column(name = "post_img")
    public String postImg;

    @Column(name = "is_recruiting")
    public boolean isRecruiting;

    @Column(name = "created_dt")
    public LocalDateTime createdDt;

    @Column(name = "closed_dt")
    public LocalDateTime closedDt;

}

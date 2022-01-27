package handong.whynot.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode(of = "id")
public class Job {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id", nullable = false)
    public Long id;

    @Column(name = "name", nullable = false)
    public String name;

//    @OneToMany(mappedBy = "job")
//    private List<JobPost> jobPosts = new ArrayList<>();

}

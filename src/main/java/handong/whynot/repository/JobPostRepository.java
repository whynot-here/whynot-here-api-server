package handong.whynot.repository;

import handong.whynot.domain.JobPost;
import handong.whynot.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    List<JobPost> findAllByPost(Post post);
}

package handong.whynot.repository;

import handong.whynot.domain.Post;
import handong.whynot.domain.PostApply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostApplyRepository extends JpaRepository<PostApply, Long> {
    List<PostApply> findAllByPost(Post post);
}

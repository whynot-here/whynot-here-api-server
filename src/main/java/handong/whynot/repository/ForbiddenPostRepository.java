package handong.whynot.repository;

import handong.whynot.domain.ForbiddenPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForbiddenPostRepository extends JpaRepository<ForbiddenPost, Long> {
}

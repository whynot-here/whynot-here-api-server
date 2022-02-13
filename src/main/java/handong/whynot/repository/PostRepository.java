package handong.whynot.repository;

import handong.whynot.domain.Account;
import handong.whynot.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndCreatedBy(Long id, Account account);

    List<Post> findAllByCreatedBy(Account account);
}

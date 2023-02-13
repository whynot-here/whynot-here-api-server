package handong.whynot.repository;

import handong.whynot.domain.Account;
import handong.whynot.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPostId(Long postId);

  List<Comment> findAllByCreatedBy(Account account);
}

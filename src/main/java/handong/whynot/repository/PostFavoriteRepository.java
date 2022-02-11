package handong.whynot.repository;

import handong.whynot.domain.Post;
import handong.whynot.domain.PostFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostFavoriteRepository extends JpaRepository<PostFavorite, Long> {
    List<PostFavorite> findAllByPost(Post post);

    List<PostFavorite> findAllByAccount(Long accountId);
}

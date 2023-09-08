package handong.whynot.repository;

import handong.whynot.dto.post.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostForCacheRepository {
  private final PostQueryRepository postQueryRepository;

  @Cacheable(value="MainPosts", key="'MainPosts'")
  public List<PostResponseDTO> getAllPostsByCache() {
    return postQueryRepository.getPostsV2().stream()
      .map(PostResponseDTO::of)
      .collect(Collectors.toList());
  }
}

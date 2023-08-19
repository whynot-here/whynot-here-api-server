package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.domain.Accusation;
import handong.whynot.domain.ForbiddenPost;
import handong.whynot.domain.Post;
import handong.whynot.dto.accusation.AccusationRequestDTO;
import handong.whynot.dto.accusation.AccusationResponseCode;
import handong.whynot.dto.accusation.AccusationResponseDTO;
import handong.whynot.dto.post.PostResponseCode;
import handong.whynot.exception.accusation.AccusationNotFoundException;
import handong.whynot.exception.post.PostNotFoundException;
import handong.whynot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccusationService {

  private final AccusationRepository accusationRepository;
  private final PostRepository postRepository;
  private final PostService postService;
  private final AdminService adminService;
  private final MobilePushService mobilePushService;
  private final ForbiddenPostRepository forbiddenPostRepository;
  private final CommentQueryRepository commentQueryRepository;

  @Transactional
  public void createAccusation(AccusationRequestDTO request, Account account) {
    Post post = postRepository.findById(request.getPostId())
      .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

    Accusation accusation = Accusation.builder()
      .reason(request.getReason())
      .reporter(account)
      .postWriterId(post.getCreatedBy().getId())
      .post(post)
      .isApproved(false)
      .build();

    accusationRepository.save(accusation);

    // 신고 당시 게시글, 댓글 캡처
    ForbiddenPost forbiddenPost = ForbiddenPost.of(post, commentQueryRepository.findCommentsByPostId(post.getId()));
    forbiddenPostRepository.save(forbiddenPost);

    // 관리자 알림
    mobilePushService.pushAdminPostAccusation(adminService.getAdminAccount(), request.getPostId());
  }

  public List<AccusationResponseDTO> getAllAccusation(Boolean isApproved) {
    return accusationRepository.findAllByIsApproved(isApproved).stream()
      .map(AccusationResponseDTO::of)
      .collect(Collectors.toList());
  }

  @Transactional
  public void approveAccusation(Long accusationId, Boolean approval) {
    Accusation accusation = accusationRepository.findById(accusationId)
      .orElseThrow(() -> new AccusationNotFoundException(AccusationResponseCode.ACCUSATION_READ_FAIL));

    accusation.updateIsApproved(approval);

    // 부적절 게시물일 경우, 삭제 조치
    if (approval) {
      postService.deletePost(accusation.getPost().getId(), accusation.getPost().getCreatedBy());
    }
  }
}

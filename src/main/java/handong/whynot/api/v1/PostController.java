package handong.whynot.api.v1;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.CurrentAccount;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.dto.job.JobType;
import handong.whynot.dto.post.*;
import handong.whynot.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Deprecated
    @Operation(summary = "공고 전체 조회")
    @GetMapping("")
    public List<PostResponseDTO> getPosts(
            @RequestParam(name = "recruit", required = false) RecruitStatus status,
            @RequestParam(name = "jobTypeList", required = false, defaultValue = "") List<JobType> jobTypeList) {

        return postService.getPostsByParam(status, jobTypeList);
    }

    @Deprecated
    @Operation(summary = "공고 생성")
    @PostMapping("")
    @ResponseStatus(CREATED)
    public ResponseDTO createPost(@RequestBody PostRequestDTO request, @CurrentAccount Account account) {

        postService.createPost(request, account);

        return ResponseDTO.of(PostResponseCode.POST_CREATE_OK);
    }

    @Deprecated
    @Operation(summary = "공고 단건 조회")
    @GetMapping("/{postId}")
    public PostResponseDTO getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @Deprecated
    @Operation(summary = "공고 단건 삭제")
    @DeleteMapping("/{postId}")
    public ResponseDTO deletePost(@PathVariable Long postId, @CurrentAccount Account account) {

        postService.deletePost(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_DELETE_OK);
    }

    @Deprecated
    @Operation(summary = "공고 단건 수정")
    @PutMapping("/{postId}")
    public ResponseDTO updatePost(@PathVariable Long postId, @RequestBody PostRequestDTO request, @CurrentAccount Account account) {

        postService.updatePost(postId, request, account);

        return ResponseDTO.of(PostResponseCode.POST_UPDATE_OK);
    }

    @Deprecated
    @Operation(summary = "‘좋아요’ 공고 전체 조회")
    @GetMapping("/favorite")
    public List<PostResponseDTO> getFavorite(@CurrentAccount Account account) {

        return postService.getFavorites(account);
    }

    @Deprecated
    @Operation(summary = "‘좋아요’ 공고 단건 생성 (좋아요 on)")
    @PostMapping("/favorite/{postId}")
    @ResponseStatus(CREATED)
    public ResponseDTO createFavorite(@PathVariable Long postId, @CurrentAccount Account account) {

        postService.createFavorite(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_CREATE_FAVORITE_OK);
    }

    @Deprecated
    @Operation(summary = "‘좋아요’ 공고 단건 삭제 (좋아요 off)")
    @DeleteMapping("/favorite/{postId}")
    public ResponseDTO deleteFavorite(@PathVariable Long postId, @CurrentAccount Account account) {

        postService.deleteFavorite(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_DELETE_FAVORITE_OK);
    }

    @Deprecated
    @Operation(summary = "신청한 공고 전체 조회")
    @GetMapping("/apply")
    public List<PostResponseDTO> getApplys(@CurrentAccount Account account) {

        return postService.getApplys(account);
    }

    @Deprecated
    @Operation(summary = "신청한 공고 단건 생성 (지원) + 이메일 알림")
    @PostMapping("/apply/{postId}")
    @ResponseStatus(CREATED)
    public ResponseDTO createApply(@PathVariable Long postId, @RequestBody PostApplyRequestDTO request, @CurrentAccount Account account) {

        postService.createApply(postId, request, account);

        return ResponseDTO.of(PostResponseCode.POST_CREATE_APPLY_OK);
    }

    @Deprecated
    @Operation(summary = "신청한 공고 단건 삭제 (지원 취소) + 이메일 알림")
    @DeleteMapping("/apply/{postId}")
    public ResponseDTO deleteApply(@PathVariable Long postId, @CurrentAccount Account account) {

        postService.deleteApply(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_DELETE_APPLY_OK);
    }

    @Deprecated
    @Operation(summary = "생성한 공고 전체 조회성")
    @GetMapping("/own")
    public List<PostResponseDTO> getMyPosts(@CurrentAccount Account account) {

        return postService.getMyPosts(account);
    }

    @Deprecated
    @Operation(summary = "공고 모집 상태변경")
    @PostMapping("/own/{postId}")
    public ResponseDTO changeRecruiting(@PathVariable Long postId, @RequestBody PostRecruitDTO request, @CurrentAccount Account account) {

        postService.changeRecruiting(postId, request, account);

        return ResponseDTO.of(PostResponseCode.POST_END_RECRUIT_OK);
    }
}
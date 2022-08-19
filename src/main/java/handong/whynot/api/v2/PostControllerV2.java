package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.dto.post.*;
import handong.whynot.service.AccountService;
import handong.whynot.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v2/posts")
@RequiredArgsConstructor
public class PostControllerV2 {

    private final PostService postService;
    private final AccountService accountService;

    @Operation(summary = "공고 전체 조회")
    @GetMapping("")
    public List<PostResponseDTO> getPostsV2(
            @RequestParam(name = "recruit", required = false) RecruitStatus status) {

        return postService.getPostsV2(status);
    }

    @Operation(summary = "선택한 카테고리 공고 전체 조회")
    @GetMapping("/category/{id}")
    public List<PostResponseDTO> getPostsByCategoryV2(@PathVariable Long id) {

        return postService.getPostsByCategory(id);
    }

    @Operation(summary = "공고 생성")
    @PostMapping("")
    @ResponseStatus(CREATED)
    public ResponseDTO createPost(@RequestBody PostRequestDTO request) {

        Account account = accountService.getCurrentAccount();
        postService.createPost(request, account);

        return ResponseDTO.of(PostResponseCode.POST_CREATE_OK);
    }

    @Operation(summary = "공고 단건 조회")
    @GetMapping("/{postId}")
    public PostResponseDTO getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @Operation(summary = "공고 단건 삭제")
    @DeleteMapping("/{postId}")
    public ResponseDTO deletePost(@PathVariable Long postId) {

        Account account = accountService.getCurrentAccount();
        postService.deletePost(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_DELETE_OK);
    }

    @Operation(summary = "공고 단건 수정")
    @PutMapping("/{postId}")
    public ResponseDTO updatePost(@PathVariable Long postId, @RequestBody PostRequestDTO request) {

        Account account = accountService.getCurrentAccount();
        postService.updatePost(postId, request, account);

        return ResponseDTO.of(PostResponseCode.POST_UPDATE_OK);
    }

    @Operation(summary = "‘좋아요’ 공고 전체 조회")
    @GetMapping("/favorite")
    public List<PostResponseDTO> getFavorite() {

        Account account = accountService.getCurrentAccount();

        return postService.getFavorites(account);
    }

    @Operation(summary = "‘좋아요’ 공고 단건 생성 (좋아요 on)")
    @PostMapping("/favorite/{postId}")
    @ResponseStatus(CREATED)
    public ResponseDTO createFavorite(@PathVariable Long postId) {

        Account account = accountService.getCurrentAccount();
        postService.createFavorite(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_CREATE_FAVORITE_OK);
    }

    @Operation(summary = "‘좋아요’ 공고 단건 삭제 (좋아요 off)")
    @DeleteMapping("/favorite/{postId}")
    public ResponseDTO deleteFavorite(@PathVariable Long postId) {

        Account account = accountService.getCurrentAccount();
        postService.deleteFavorite(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_DELETE_FAVORITE_OK);
    }

    @Operation(summary = "신청한 공고 전체 조회")
    @GetMapping("/apply")
    public List<PostResponseDTO> getApplys() {

        Account account = accountService.getCurrentAccount();
        return postService.getApplys(account);
    }

    @Operation(summary = "신청한 공고 단건 생성 (지원) + 이메일 알림")
    @PostMapping("/apply/{postId}")
    @ResponseStatus(CREATED)
    public ResponseDTO createApply(@PathVariable Long postId, @RequestBody PostApplyRequestDTO request) {

        Account account = accountService.getCurrentAccount();
        postService.createApply(postId, request, account);

        return ResponseDTO.of(PostResponseCode.POST_CREATE_APPLY_OK);
    }

    @Operation(summary = "신청한 공고 단건 삭제 (지원 취소) + 이메일 알림")
    @DeleteMapping("/apply/{postId}")
    public ResponseDTO deleteApply(@PathVariable Long postId) {

        Account account = accountService.getCurrentAccount();
        postService.deleteApply(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_DELETE_APPLY_OK);
    }

    @Operation(summary = "생성한 공고 전체 조회성")
    @GetMapping("/own")
    public List<PostResponseDTO> getMyPosts() {

        Account account = accountService.getCurrentAccount();

        return postService.getMyPosts(account);
    }

    @Operation(summary = "공고 모집 상태변경")
    @PostMapping("/own/{postId}")
    public ResponseDTO changeRecruiting(@PathVariable Long postId, @RequestBody PostRecruitDTO request) {

        Account account = accountService.getCurrentAccount();
        postService.changeRecruiting(postId, request, account);

        return ResponseDTO.of(PostResponseCode.POST_END_RECRUIT_OK);
    }
}
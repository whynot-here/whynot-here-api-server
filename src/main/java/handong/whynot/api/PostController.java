package handong.whynot.api;

import handong.whynot.domain.Account;
import handong.whynot.dto.account.CurrentAccount;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.dto.job.JobType;
import handong.whynot.dto.post.*;
import handong.whynot.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public List<PostResponseDTO> getPosts(
            @RequestParam(name = "recruit", required = false) RecruitStatus status,
            @RequestParam(name = "jobTypeList", required = false, defaultValue = "") List<JobType> jobTypeList) {

        return postService.getPostsByParam(status, jobTypeList);
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    public ResponseDTO createPost(@RequestBody PostRequestDTO request, @CurrentAccount Account account) {

        postService.createPost(request, account);

        return ResponseDTO.of(PostResponseCode.POST_CREATE_OK);
    }

    @GetMapping("/{postId}")
    public PostResponseDTO getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }

    @DeleteMapping("/{postId}")
    public ResponseDTO deletePost(@PathVariable Long postId, @CurrentAccount Account account) {

        postService.deletePost(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_DELETE_OK);
    }

    @PutMapping("/{postId}")
    public ResponseDTO updatePost(@PathVariable Long postId, @RequestBody PostRequestDTO request, @CurrentAccount Account account) {

        postService.updatePost(postId, request, account);

        return ResponseDTO.of(PostResponseCode.POST_UPDATE_OK);
    }
  
    @GetMapping("/favorite")
    public List<PostResponseDTO> getFavorite(@CurrentAccount Account account) {

        return postService.getFavorites(account);
    }

    @PostMapping("/favorite/{postId}")
    @ResponseStatus(CREATED)
    public ResponseDTO createFavorite(@PathVariable Long postId, @CurrentAccount Account account) {

        postService.createFavorite(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_CREATE_FAVORITE_OK);
    }
  
    @DeleteMapping("/favorite/{postId}")
    public ResponseDTO deleteFavorite(@PathVariable Long postId, @CurrentAccount Account account) {

        postService.deleteFavorite(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_DELETE_FAVORITE_OK);
    }
  
    @GetMapping("/apply")
    public List<PostResponseDTO> getApplys(@CurrentAccount Account account) {

        return postService.getApplys(account);
    }

  
    @PostMapping("/apply/{postId}")
    @ResponseStatus(CREATED)
    public ResponseDTO createApply(@PathVariable Long postId, @RequestBody PostApplyRequestDTO request, @CurrentAccount Account account) {

        postService.createApply(postId, request, account);

        return ResponseDTO.of(PostResponseCode.POST_CREATE_APPLY_OK);
    }
  
    @DeleteMapping("/apply/{postId}")
    public ResponseDTO deleteApply(@PathVariable Long postId, @CurrentAccount Account account) {

        postService.deleteApply(postId, account);

        return ResponseDTO.of(PostResponseCode.POST_DELETE_APPLY_OK);
    }
  
    @GetMapping("/own")
    public List<PostResponseDTO> getMyPosts(@CurrentAccount Account account) {

        return postService.getMyPosts(account);
    }

    @PostMapping("/own/{postId}")
    public ResponseDTO changeRecruiting(@PathVariable Long postId, @RequestBody PostRecruitDTO request, @CurrentAccount Account account) {

        postService.changeRecruiting(postId, request, account);

        return ResponseDTO.of(PostResponseCode.POST_END_RECRUIT_OK);
    }
}
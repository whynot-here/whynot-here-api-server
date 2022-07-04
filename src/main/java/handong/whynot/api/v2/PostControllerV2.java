package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.dto.post.PostRequestDTO;
import handong.whynot.dto.post.PostResponseCode;
import handong.whynot.service.AccountService;
import handong.whynot.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v2/posts")
@RequiredArgsConstructor
public class PostControllerV2 {

    private final PostService postService;
    private final AccountService accountService;

    @Operation(summary = "공고 생성")
    @PostMapping("")
    @ResponseStatus(CREATED)
    public ResponseDTO createPost(@RequestBody PostRequestDTO request) {

        Account account = accountService.getCurrentAccount();
        postService.createPost(request, account);

        return ResponseDTO.of(PostResponseCode.POST_CREATE_OK);
    }
}
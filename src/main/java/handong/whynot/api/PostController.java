package handong.whynot.api;

import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.dto.post.PostRequestDTO;
import handong.whynot.dto.post.PostResponseCode;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public List<PostResponseDTO> getPosts() {

        return postService.getPosts();
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    public ResponseDTO createPost(@RequestBody PostRequestDTO request) {

        postService.createPost(request);

        return ResponseDTO.of(PostResponseCode.POST_CREATE_OK);
    }

}

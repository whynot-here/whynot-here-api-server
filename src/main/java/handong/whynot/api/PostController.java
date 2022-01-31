package handong.whynot.api;

import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public List<PostResponseDTO> getPosts() {

        return postService.getPosts();
    }

//    @PostMapping("")
//    public void

}

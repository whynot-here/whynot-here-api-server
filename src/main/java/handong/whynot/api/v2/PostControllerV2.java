package handong.whynot.api.v2;

import handong.whynot.dto.job.JobType;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.dto.post.RecruitStatus;
import handong.whynot.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v2/posts")
@RequiredArgsConstructor
public class PostControllerV2 {

    private final PostService postService;

    @Operation(summary = "공고 전체 조회")
    @GetMapping("")
    public List<PostResponseDTO> getPosts(
            @RequestParam(name = "recruit", required = false) RecruitStatus status,
            @RequestParam(name = "jobs", required = false, defaultValue = "") List<JobType> jobTypeList) {

        return postService.getPostsByParam(status, jobTypeList);
    }
}
package handong.whynot.service;

import handong.whynot.domain.Post;
import handong.whynot.dto.job.JobEnum;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.repository.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostQueryRepository postQueryRepository;

    public List<PostResponseDTO> getPosts() {

        List<Post> posts = postQueryRepository.getPosts();
        return posts.stream()
                .map(post -> PostResponseDTO.builder()
                                .id(post.id)
                                .title(post.title)
                                .postImg(post.postImg)
                                .createdDt(post.createdDt)
                                .createdBy(post.createdBy)
                                .content(post.content)
                                .isRecruiting(post.isRecruiting)
                                .jobs(postQueryRepository.getJobs(post.id))
                                .applicants(postQueryRepository.getApplicants(post.id))
                                .build()
                        )
                .collect(Collectors.toList());
    }
}

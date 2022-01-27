package handong.whynot.service;

import handong.whynot.dto.job.JobEnum;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.repository.PostQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostQueryRepository postQueryRepository;

    public List<PostResponseDTO> getPosts() {
        List<PostResponseDTO> dtos = postQueryRepository.getPosts();

        dtos.forEach(it -> {
            it.setJobs(postQueryRepository.getJobs(it.id));
            it.setApplicants(postQueryRepository.getApplicants(it.id));
        });

        return dtos;
    }


}

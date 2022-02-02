package handong.whynot.service;

import handong.whynot.domain.Job;
import handong.whynot.domain.JobPost;
import handong.whynot.domain.Post;
import handong.whynot.domain.User;
import handong.whynot.dto.job.JobResponseCode;
import handong.whynot.dto.post.PostRequestDTO;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.dto.user.UserResponseCode;
import handong.whynot.exception.job.JobNotFoundException;
import handong.whynot.exception.user.UserNotFoundException;
import handong.whynot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final JobRepository jobRepository;
    private final JobPostRepository jobPostRepository;
    private final UserRepository userRepository;

    public List<PostResponseDTO> getPosts() {

        List<Post> posts = postQueryRepository.getPosts();
        return posts.stream()
                .map(post ->
                        PostResponseDTO.of(post,
                                postQueryRepository.getJobs(post.getId()),
                                postQueryRepository.getApplicants(post.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    public void createPost(PostRequestDTO request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(
                        () -> new UserNotFoundException(UserResponseCode.USER_READ_FAIL));

        // 1. Post 저장
        Post post = Post.builder()
                .createdBy(user)
                .title(request.getTitle())
                .content(request.getContent())
                .postImg(request.getPostImg())
                .build();
        Post newPost = postRepository.save(post);

        // 2. JobPost 저장
        request.jobIds.forEach(
                id -> {
                    Job job = jobRepository.findById(id)
                            .orElseThrow(
                                    () -> new JobNotFoundException(JobResponseCode.JOB_READ_FAIL));

                    JobPost jobPost = JobPost.builder()
                            .job(job)
                            .post(newPost)
                            .build();

                    jobPostRepository.save(jobPost);
                }
        );
    }
}

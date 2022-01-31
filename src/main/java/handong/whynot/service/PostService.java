package handong.whynot.service;

import handong.whynot.domain.Job;
import handong.whynot.domain.JobPost;
import handong.whynot.domain.Post;
import handong.whynot.domain.User;
import handong.whynot.dto.job.JobEnum;
import handong.whynot.dto.job.JobResponseCode;
import handong.whynot.dto.post.PostRequestDTO;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.dto.user.UserResponseCode;
import handong.whynot.exception.job.JobNotFoundException;
import handong.whynot.exception.user.UserNotFoundException;
import handong.whynot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .map(post -> PostResponseDTO.builder()
                                .id(post.id)
                                .title(post.title)
                                .postImg(post.postImg)
                                .createdDt(post.createdDt)
                                .updatedDt(post.updatedDt)
                                .createdBy(post.createdBy)
                                .content(post.content)
                                .isRecruiting(post.isRecruiting)
                                .jobs(postQueryRepository.getJobs(post.id))
                                .applicants(postQueryRepository.getApplicants(post.id))
                                .build()
                        )
                .collect(Collectors.toList());
    }

    public Post createPost(PostRequestDTO request) {

        User user = userRepository.findById(request.userId)
                .orElseThrow(() -> new UserNotFoundException(UserResponseCode.USER_READ_FAIL));

        // 1. Post 저장
        Post post = Post.builder()
                        .createdBy(user)
                        .title(request.title)
                        .content(request.content)
                        .postImg(request.postImg)
                        .build();
        post.isRecruiting = true;
        Post newPost = postRepository.save(post);

        // 2. Job 저장
        List<JobPost> jobPosts = new ArrayList<>();
        request.jobIds.forEach(
                id -> {
                    Job job = jobRepository.findById(id)
                            .orElseThrow(() -> new JobNotFoundException(JobResponseCode.JOB_READ_FAIL));

                    JobPost jobPost = JobPost.builder()
                                    .job(job)
                                    .post(newPost)
                                    .build();

                    jobPosts.add(jobPost);
                    jobPostRepository.save(jobPost);
                }
        );
        post.addJobs(jobPosts);

        return post;
    }
}

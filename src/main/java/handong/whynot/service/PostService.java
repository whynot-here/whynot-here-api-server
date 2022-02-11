package handong.whynot.service;

import handong.whynot.domain.*;
import handong.whynot.dto.job.JobResponseCode;
import handong.whynot.dto.post.PostRequestDTO;
import handong.whynot.dto.post.PostResponseCode;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.exception.job.JobNotFoundException;
import handong.whynot.exception.post.PostNotFoundException;
import handong.whynot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final JobRepository jobRepository;
    private final JobPostRepository jobPostRepository;
    private final AccountRepository accountRepository;
    private final PostFavoriteRepository postFavoriteRepository;
    private final PostApplyRepository postApplyRepository;
    
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
    public Post createPost(PostRequestDTO request, Account account) {

        // 1. Post 저장
        Post post = Post.builder()
                .createdBy(account)
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

        return newPost;
    }

    public PostResponseDTO getPost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        return PostResponseDTO.of(post,
                postQueryRepository.getJobs(post.getId()),
                postQueryRepository.getApplicants(post.getId()));
    }

    public void deletePost(Long id, Account account) {
        Post post = postRepository.findByIdAndCreatedBy(id, account)
                .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // 1. job 삭제
        deleteJobPosts(post);

        // 2. favorite 삭제
        deletePostFavorites(post);

        // 3. apply 삭제
        deletePostApplys(post);

        postRepository.delete(post);
    }

    public void deleteJobPosts(Post post) {

        List<JobPost> jobPosts = jobPostRepository.findAllByPost(post);
        jobPosts.forEach(jobPost -> jobPostRepository.deleteById(jobPost.getId()));

    }

    public void deletePostFavorites(Post post) {

        List<PostFavorite> postFavorites = postFavoriteRepository.findAllByPost(post);
        postFavorites.forEach(postFavorite -> postFavoriteRepository.deleteById(postFavorite.getId()));

    }

    public void deletePostApplys(Post post) {

        List<PostApply> postApplys = postApplyRepository.findAllByPost(post);
        postApplys.forEach(postApply -> postApplyRepository.deleteById(postApply.getId()));

    }

    public void updatePost(Long postId, PostRequestDTO request, Account account) {

        Post post = postRepository.findByIdAndCreatedBy(postId, account)
                .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        post.update(request);

        postRepository.save(post);

    }

    public List<PostResponseDTO> getApplys(Account account) {

        List<Post> posts = postQueryRepository.getApplys(account);

        return posts.stream()
                .map(post ->
                        PostResponseDTO.of(post,
                                postQueryRepository.getJobs(post.getId()),
                                postQueryRepository.getApplicants(post.getId())))
                .collect(Collectors.toList());
    }
}

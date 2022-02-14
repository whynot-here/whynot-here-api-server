package handong.whynot.service;

import handong.whynot.domain.*;
import handong.whynot.dto.job.JobResponseCode;
import handong.whynot.dto.post.*;
import handong.whynot.exception.job.JobNotFoundException;
import handong.whynot.exception.post.*;
import handong.whynot.mail.EmailMessage;
import handong.whynot.mail.EmailService;
import handong.whynot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static handong.whynot.dto.job.JobEnum.getJobInfoBy;

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
    private final EmailService emailService;

    public List<PostResponseDTO> getPostsByParam(String recruit, String jobs) {

        Optional<PostStatus> status = PostStatus.getStatusBy(recruit);
        if (status.isPresent()) {
            Boolean isRecruiting = status.get().getIsRecruiting();
            List<Job> jobList = getJobInfoBy(jobs);

            if (!jobList.isEmpty()) {
                getPostByStatusAndJob(isRecruiting, jobList);
            }

            return getPostByStatus(isRecruiting);
        }
        return getPosts();
    }

    public List<PostResponseDTO> getPostByStatus(Boolean isRecruiting) {

        List<Post> posts = postQueryRepository.getPostByStatus(isRecruiting);

        return posts.stream()
                .map(post ->
                        PostResponseDTO.of(post,
                                postQueryRepository.getJobs(post.getId()),
                                postQueryRepository.getApplicants(post.getId())))
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostByStatusAndJob(Boolean isRecruiting, List<Job> jobList) {

        List<Post> posts = postQueryRepository.getPostByStatusAndJob(isRecruiting, jobList);

        return posts.stream()
                .map(post ->
                        PostResponseDTO.of(post,
                                postQueryRepository.getJobs(post.getId()),
                                postQueryRepository.getApplicants(post.getId())))
                .collect(Collectors.toList());
    }
    
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
                .isRecruiting(true)
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


    public List<PostResponseDTO> getFavorites(Account account) {

        List<Post> posts = postQueryRepository.getFavorites(account);

        return posts.stream()
                .map(post ->
                        PostResponseDTO.of(post,
                                postQueryRepository.getJobs(post.getId()),
                                postQueryRepository.getApplicants(post.getId())))
                .collect(Collectors.toList());
    }

    public void createFavorite(Long postId, Account account) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        if (!postQueryRepository.getFavoriteByPostId(post, account).isEmpty()) {
            throw new PostAlreadyFavoriteOn(PostResponseCode.POST_CREATE_FAVORITE_FAIL);
        }

        PostFavorite postFavorite = PostFavorite.builder()
                .post(post)
                .account(account)
                .build();

        postFavoriteRepository.save(postFavorite);

    }
  
    public void deleteFavorite(Long postId, Account account) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        List<PostFavorite> favorite = postQueryRepository.getFavoriteByPostId(post, account);
        if (favorite.isEmpty()) {
            throw new PostAlreadyFavoriteOff(PostResponseCode.POST_DELETE_FAVORITE_FAIL);
        }

        postFavoriteRepository.deleteById(favorite.get(0).getId());

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

    @Transactional
    public void createApply(Long postId, PostApplyRequestDTO request, Account account) {

        List<Post> posts = postQueryRepository.getEnabledPost(postId);
        Post post = posts.stream().findFirst()
                .orElseThrow(() -> new PostAlreadyApplyOn(PostResponseCode.POST_CREATE_APPLY_FAIL));

        Job job = jobRepository.findById(request.getJob())
                .orElseThrow(() -> new JobNotFoundException(JobResponseCode.JOB_READ_FAIL));

        if (!postQueryRepository.getApplyByPostId(post, account).isEmpty()) {
            throw new PostAlreadyApplyOn(PostResponseCode.POST_CREATE_APPLY_FAIL);
        }

        PostApply postApply = PostApply.builder()
                .post(post)
                .job(job)
                .account(account)
                .build();

        postApplyRepository.save(postApply);

        // 이메일 전송
        String message = account.getNickname() + " 님이"
                + post.getTitle() + " 공고에 "
                + job.getName() + " 직무로 지원 요청을 하였습니다.";

        EmailMessage emailMessage = EmailMessage.builder()
                .to(post.getCreatedBy().getEmail())
                .subject("[공고 지원 알림] "+post.getTitle()+" by "+account.getNickname())
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }

    @Transactional
    public void deleteApply(Long postId, Account account) {

        List<Post> posts = postQueryRepository.getEnabledPost(postId);
        Post post = posts.stream().findFirst()
                .orElseThrow(() -> new PostAlreadyApplyOn(PostResponseCode.POST_CREATE_APPLY_FAIL));

        List<PostApply> applies = postQueryRepository.getApplyByPostId(post, account);
        if (applies.isEmpty()) {
            throw new PostAlreadyApplyOff(PostResponseCode.POST_DELETE_APPLY_FAIL);
        }

        PostApply apply = applies.get(0);

        postApplyRepository.deleteById(apply.getId());

        // 이메일 전송
        String message = account.getNickname() + " 님이"
                + post.getTitle() + " 공고에 "
                + apply.getJob().getName() + " 직무 지원 요청을 취소하였습니다.";

        EmailMessage emailMessage = EmailMessage.builder()
                .to(post.getCreatedBy().getEmail())
                .subject("[공고 지원 취소 알림] "+post.getTitle()+" by "+account.getNickname())
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);

    }
  
    public List<PostResponseDTO> getMyPosts(Account account) {

        List<Post> posts = postRepository.findAllByCreatedBy(account);

        return posts.stream()
                .map(post ->
                        PostResponseDTO.of(post,
                                postQueryRepository.getJobs(post.getId()),
                                postQueryRepository.getApplicants(post.getId())))
                .collect(Collectors.toList());
    }

    public void changeRecruiting(Long postId, PostRecruitDTO dto, Account account) {

        Post post = postRepository.findByIdAndCreatedBy(postId, account)
                .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        post.setRecruiting(dto.getIsRecruit());
        postRepository.save(post);
    }
}
package handong.whynot.service;

import handong.whynot.domain.*;
import handong.whynot.dto.account.AccountResponseDTO;
import handong.whynot.dto.category.CategoryResponseCode;
import handong.whynot.dto.job.JobResponseCode;
import handong.whynot.dto.job.JobType;
import handong.whynot.dto.post.*;
import handong.whynot.exception.category.CategoryNotFoundException;
import handong.whynot.exception.job.JobNotFoundException;
import handong.whynot.exception.post.*;
import handong.whynot.mail.EmailMessage;
import handong.whynot.mail.EmailService;
import handong.whynot.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static handong.whynot.dto.job.JobType.getJobInfoBy;

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
    private final CategoryRepository categoryRepository;

    public List<PostResponseDTO> getPostsByParam(RecruitStatus recruitStatus, List<JobType> jobTypeList) {

        // recruit, jobs 모두 있는 경우
        if (recruitStatus != null & !jobTypeList.isEmpty()) {

            Boolean isRecruiting = recruitStatus.getIsRecruiting();
            List<Job> jobs = getJobInfoBy(jobTypeList);

            return getPostByRecruitAndJob(isRecruiting, jobs);
        }

        // recruit만 있는 경우
        if (recruitStatus != null) {

            Boolean isRecruiting = recruitStatus.getIsRecruiting();

            return getPostByRecruit(isRecruiting);
        }

        // jobs만 있는 경우
        if (!jobTypeList.isEmpty()) {

            List<Job> jobs = getJobInfoBy(jobTypeList);

            return getPostByJob(jobs);
        }

        // 모두 없는 경우
        return getPosts();
    }

    public List<PostResponseDTO> getPostByRecruitAndJob(Boolean isRecruiting, List<Job> jobList) {

        List<Post> posts = postQueryRepository.getPostByRecruitAndJob(isRecruiting, jobList);

        return posts.stream()
                .map(post ->
                        PostResponseDTO.of(post,
                                postQueryRepository.getJobs(post.getId()),
                                getApplicants(post.getId())))
                .collect(Collectors.toList());
    }

    public List<AccountResponseDTO> getApplicants(Long postId) {
        return postQueryRepository.getApplicants(postId).stream()
                .map(Account::getAccountDTO)
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostByRecruit(Boolean isRecruiting) {

        List<Post> posts = postQueryRepository.getPostByRecruit(isRecruiting);

        return posts.stream()
                .map(post ->
                        PostResponseDTO.of(post,
                                postQueryRepository.getJobs(post.getId()),
                                getApplicants(post.getId())))
                .collect(Collectors.toList());
    }

    public List<PostResponseDTO> getPostByJob(List<Job> jobs) {

        List<Post> posts = postQueryRepository.getPostByJob(jobs);

        return posts.stream()
                .map(post ->
                        PostResponseDTO.of(post,
                                postQueryRepository.getJobs(post.getId()),
                                getApplicants(post.getId())))
                .collect(Collectors.toList());
    }
    
    public List<PostResponseDTO> getPosts() {

        List<Post> posts = postQueryRepository.getPosts();
        return posts.stream()
                .map(post ->
                        PostResponseDTO.of(post,
                                postQueryRepository.getJobs(post.getId()),
                                getApplicants(post.getId())))
                .collect(Collectors.toList());
    }

    @Transactional
    public Post createPost(PostRequestDTO request, Account account) {

        // 1. 카테고리 조회
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(CategoryResponseCode.CATEGORY_READ_FAIL));

        // 2. Post 저장
        Post post = Post.builder()
                .createdBy(account)
                .title(request.getTitle())
                .content(request.getContent())
                .postImg(request.getPostImg())
                .categoryId(category)
                .closedDt(request.getClosedDt())
                .ownerContact(request.getOwnerContact())
                .recruitTotalCnt(request.getRecruitTotalCnt())
                .recruitCurrentCnt(request.getRecruitCurrentCnt())
                .communicationTool(request.getCommunicationTool())
                .isRecruiting(true)

                .build();
        Post newPost = postRepository.save(post);

        return newPost;
    }

    public PostResponseDTO getPost(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        return PostResponseDTO.of(post,
                postQueryRepository.getJobs(post.getId()),
                getApplicants(post.getId()));
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
                                getApplicants(post.getId())))
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
                                getApplicants(post.getId())))
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
                                getApplicants(post.getId())))
                .collect(Collectors.toList());
    }

    public void changeRecruiting(Long postId, PostRecruitDTO dto, Account account) {

        Post post = postRepository.findByIdAndCreatedBy(postId, account)
                .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        post.setRecruiting(dto.getIsRecruit());
        postRepository.save(post);
    }
}
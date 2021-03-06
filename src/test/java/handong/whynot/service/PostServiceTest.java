package handong.whynot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import handong.whynot.domain.Account;
import handong.whynot.domain.Job;
import handong.whynot.domain.JobPost;
import handong.whynot.domain.Post;
import handong.whynot.domain.PostApply;
import handong.whynot.domain.PostFavorite;
import handong.whynot.dto.account.AccountResponseDTO;
import handong.whynot.dto.job.JobResponseCode;
import handong.whynot.dto.post.PostApplyRequestDTO;
import handong.whynot.dto.post.PostRecruitDTO;
import handong.whynot.dto.post.PostRequestDTO;
import handong.whynot.dto.post.PostResponseCode;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.exception.job.JobNotFoundException;
import handong.whynot.exception.post.PostAlreadyApplyOff;
import handong.whynot.exception.post.PostAlreadyApplyOn;
import handong.whynot.exception.post.PostAlreadyFavoriteOff;
import handong.whynot.exception.post.PostAlreadyFavoriteOn;
import handong.whynot.exception.post.PostNotFoundException;
import handong.whynot.mail.EmailService;
import handong.whynot.repository.AccountRepository;
import handong.whynot.repository.JobPostRepository;
import handong.whynot.repository.JobRepository;
import handong.whynot.repository.PostApplyRepository;
import handong.whynot.repository.PostFavoriteRepository;
import handong.whynot.repository.PostQueryRepository;
import handong.whynot.repository.PostRepository;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostQueryRepository postQueryRepository;
    @Mock
    private JobRepository jobRepository;
    @Mock
    private JobPostRepository jobPostRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PostFavoriteRepository postFavoriteRepository;
    @Mock
    private PostApplyRepository postApplyRepository;
    @Mock
    private EmailService emailService;

    @InjectMocks
    private PostService postService;

    @DisplayName("?????? ?????? ?????? ??????")
    @Test
    void getPostsTest() {

        // given, ???????????? ?????? ??????(mock ????????? ?????? ??????, ????????? ????????? ??????????????? ???)
        Account account = Account.builder().build();

        Post post1 = Post.builder().createdBy(account).content("content1").build();
        Post post2 = Post.builder().createdBy(account).content("content2").build();
        Post post3 = Post.builder().createdBy(account).content("content3").build();
        List<Post> posts = Arrays.asList(post1, post2, post3);
        final int totalPostCount = posts.size();

        when(postQueryRepository.getPosts()).thenReturn(posts);
        when(postQueryRepository.getJobs(any())).thenReturn(new ArrayList<>());

        // when, ?????? ??????????????? ?????? ???????????? ??????
        List<PostResponseDTO> postResponseDTOList = postService.getPosts();

        // then, ????????? ????????? ?????? ??????
        assertEquals(totalPostCount, postResponseDTOList.size());
    }

    @DisplayName("???????????? [??????2] - ???????????? ?????? ??????")
    @Test
    void createPostWithJobNotFoundException() {
        // given
        Account account = Account.builder().id(1L).build();

        List<Long> jobIds = Arrays.asList(1L, 2L, 200L);
        Long notExistJobId = jobIds.get(jobIds.size() - 1);
        when(jobRepository.findById(jobIds.get(0))).thenReturn(Optional.ofNullable(Job.builder().build()));
        when(jobRepository.findById(jobIds.get(1))).thenReturn(Optional.ofNullable(Job.builder().build()));
        when(jobRepository.findById(notExistJobId))
            .thenThrow(new JobNotFoundException(JobResponseCode.JOB_READ_FAIL));

        PostRequestDTO requestDTO = PostRequestDTO.builder()
                                                  .jobIds(jobIds)
                                                  .build();

        // when, then
        JobNotFoundException exception =
            assertThrows(JobNotFoundException.class, () -> postService.createPost(requestDTO, account));
        assertEquals(JobResponseCode.JOB_READ_FAIL, exception.getResponseCode());
        verify(postRepository, times(1)).save(any());
    }

    @DisplayName("?????? ?????? ?????? [??????] - ????????? ?????? ??????")
    @Test
    void getPostNotFoundPostException() {

        // given
        Account account = Account.builder().build();

        Long notExistId = 12345L;
        Post post = Post.builder().id(notExistId).createdBy(account).build();
        PostResponseDTO notExistResponse = PostResponseDTO.of(post, new ArrayList<>(), new ArrayList<>());
        when(postRepository.findById(notExistId)).thenThrow(
            new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // when, then
        PostNotFoundException exception =
            assertThrows(PostNotFoundException.class, () -> postService.getPost(notExistId));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());
    }

    @DisplayName("?????? ?????? ?????? ??????")
    @Test
    void getPostOK() {

        // given
        Account account = Account.builder().build();

        Post post = Post.builder().id(1L).createdBy(account).build();
        List<Job> jobs = new ArrayList<>();
        List<AccountResponseDTO> applicants = new ArrayList<>();

        PostResponseDTO expectedResponse = PostResponseDTO.of(post, jobs, applicants);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(postQueryRepository.getJobs(post.getId())).thenReturn(jobs);

        // when
        PostResponseDTO actualResponse = postService.getPost(post.getId());

        // then
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

    @DisplayName("?????? ?????? ?????? [??????1] - ?????? ????????? ?????? ??????")
    @Test
    void deletePostNotFoundException1() {

        // given
        Long currentAccountId = 1L;
        Long postOwnerAccountId = 2L;
        Long postId = 1L;

        Account currentAccount = Account.builder().id(currentAccountId).build();
        Account postOwnerAccount = Account.builder().id(postOwnerAccountId).build();

        Post post = Post.builder().id(postId).createdBy(postOwnerAccount).build();

        // when, then
        PostNotFoundException exception =
            assertThrows(PostNotFoundException.class, () -> postService.deletePost(postId, currentAccount));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());
        verify(jobPostRepository, never()).findAllByPost(any());
    }

    @DisplayName("?????? ?????? ?????? [??????2] - ?????? ???????????? ???????????? ?????? ????????? ??????")
    @Test
    void deletePostNotFoundException2() {

        // given
        Long currentAccountId = 1L;
        Long postOwnerAccountId = currentAccountId;
        Long postId = 1L;
        Long notExistPostId = 12345L;

        Account currentAccount = Account.builder().id(currentAccountId).build();
        Account postOwnerAccount = Account.builder().id(postOwnerAccountId).build();

        Post post = Post.builder().id(postId).createdBy(postOwnerAccount).build();

        // when, then
        PostNotFoundException exception =
            assertThrows(PostNotFoundException.class,
                         () -> postService.deletePost(notExistPostId, currentAccount));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());
        verify(jobPostRepository, never()).findAllByPost(any());
    }

    @DisplayName("?????? ?????? ???????????? [??????1] - ?????? ????????? ?????? ??????")
    @Test
    void updatePostNotFoundException1() {

        // given
        Long currentAccountId = 1L;
        Long postId = 1L;

        Account currentAccount = Account.builder().id(currentAccountId).build();

        PostRequestDTO dto = PostRequestDTO.builder()
                                           .title("?????? ??????")
                                           .content("?????? ??????")
                                           .postImg("http://image-edited.com")
                                           .build();

        when(postRepository.findByIdAndCreatedBy(postId, currentAccount))
            .thenThrow(new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // when, then
        PostNotFoundException exception =
            assertThrows(PostNotFoundException.class,
                         () -> postService.updatePost(postId, dto, currentAccount));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());
        verify(postRepository, never()).save(any());

    }

    @DisplayName("?????? ?????? ???????????? [??????2] - ?????? ???????????? ???????????? ?????? ????????? ??????")
    @Test
    void updatePostNotFoundException2() {

        // given
        Long currentAccountId = 1L;
        Long notExistPostId = 12345L;

        Account currentAccount = Account.builder().id(currentAccountId).build();

        PostRequestDTO dto = PostRequestDTO.builder()
                                           .title("?????? ??????")
                                           .content("?????? ??????")
                                           .postImg("http://image-edited.com")
                                           .build();
        when(postRepository.findByIdAndCreatedBy(notExistPostId, currentAccount))
            .thenThrow(new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // when, then
        PostNotFoundException exception =
            assertThrows(PostNotFoundException.class,
                         () -> postService.updatePost(notExistPostId, dto, currentAccount));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());
        verify(postRepository, never()).save(any());
    }

    @DisplayName("????????? ???????????? ?????? ??????")
    @Test
    void deleteJobPostsTest() {

        // given
        Post post = Post.builder().build();
        JobPost jobPost1 = JobPost.builder().id(1L).post(post).build();
        JobPost jobPost2 = JobPost.builder().id(2L).post(post).build();

        List<JobPost> jobPosts = Arrays.asList(jobPost1, jobPost2);

        when(jobPostRepository.findAllByPost(post)).thenReturn(jobPosts);
        doNothing().when(jobPostRepository).deleteById(any());

        // when
        postService.deleteJobPosts(post);

        // then
        verify(jobPostRepository, times(1)).findAllByPost(post);
        verify(jobPostRepository, times(jobPosts.size())).deleteById(anyLong());
    }

    @DisplayName("????????? ???????????? ????????? ?????? ??????")
    @Test
    void deletePostFavoritesTest() {

        // given
        Post post = Post.builder().build();

        PostFavorite postFavorite1 = PostFavorite.builder().id(1L).post(post).build();
        PostFavorite postFavorite2 = PostFavorite.builder().id(2L).post(post).build();

        List<PostFavorite> postFavorites = Arrays.asList(postFavorite1, postFavorite2);
        when(postFavoriteRepository.findAllByPost(post)).thenReturn(postFavorites);
        doNothing().when(postFavoriteRepository).deleteById(anyLong());

        // when
        postService.deletePostFavorites(post);

        // then
        verify(postFavoriteRepository, times(1)).findAllByPost(post);
        verify(postFavoriteRepository, times(postFavorites.size())).deleteById(anyLong());
    }

    @DisplayName("????????? ???????????? ?????? ?????? ??????")
    @Test
    void deletePostApplysTest() {

        // given
        Post post = Post.builder().build();

        PostApply postApply1 = PostApply.builder().id(1L).post(post).build();
        PostApply postApply2 = PostApply.builder().id(2L).post(post).build();

        List<PostApply> postApplys = Arrays.asList(postApply1, postApply2);
        when(postApplyRepository.findAllByPost(post)).thenReturn(postApplys);
        doNothing().when(postApplyRepository).deleteById(anyLong());

        // when
        postService.deletePostApplys(post);

        // then
        verify(postApplyRepository, times(1)).findAllByPost(post);
        verify(postApplyRepository, times(postApplys.size())).deleteById(anyLong());
    }

    @DisplayName("????????? ?????? ?????? ??????")
    @Test
    void getFavoritesTest() {

        // given
        Account account = Account.builder().build();

        Post post1 = Post.builder().createdBy(account).content("content1").build();
        Post post2 = Post.builder().createdBy(account).content("content2").build();
        Post post3 = Post.builder().createdBy(account).content("content3").build();
        List<Post> posts = Arrays.asList(post1, post2, post3);
        final int totalPostCount = posts.size();

        when(postQueryRepository.getFavorites(account)).thenReturn(posts);
        when(postQueryRepository.getJobs(any())).thenReturn(new ArrayList<>());

        // when
        List<PostResponseDTO> postResponseDTOList = postService.getFavorites(account);

        // then
        assertEquals(totalPostCount, postResponseDTOList.size());
    }

    @DisplayName("????????? on [??????1] - ?????? ????????? ??????")
    @Test
    void createFavoriteNotFoundPostException() {

        // given
        Account account = Account.builder().build();
        Long notExistId = 12345L;
        when(postRepository.findById(notExistId)).thenThrow(
            new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // when, then
        PostNotFoundException exception =
            assertThrows(PostNotFoundException.class, () -> postService.createFavorite(notExistId, account));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());

    }

    @DisplayName("????????? on [??????2] - ?????? ????????? ?????? ????????? ??????")
    @Test
    void createFavoriteAlreadyOnException() {

        // given
        Account account = Account.builder().build();
        Post post = Post.builder().id(1L).build();
        PostFavorite postFavorite = PostFavorite.builder().build();

        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(post));
        when(postQueryRepository.getFavoriteByPostId(post, account)).thenReturn(List.of(postFavorite));

        // when, then
        PostAlreadyFavoriteOn exception =
            assertThrows(PostAlreadyFavoriteOn.class, () -> postService.createFavorite(post.getId(), account));
        assertEquals(PostResponseCode.POST_CREATE_FAVORITE_FAIL, exception.getResponseCode());
    }

    @DisplayName("????????? off [??????1] - ?????? ????????? ??????")
    @Test
    void deleteFavoriteNotFoundPostException() {

        // given
        Account account = Account.builder().build();
        Long notExistId = 12345L;
        when(postRepository.findById(notExistId)).thenThrow(
            new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // when, then
        PostNotFoundException exception =
            assertThrows(PostNotFoundException.class, () -> postService.deleteFavorite(notExistId, account));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());

    }

    @DisplayName("????????? off [??????2] - ?????? ????????? ????????? ????????? ??????")
    @Test
    void deleteFavoriteAlreadyOffException() {

        // given
        Account account = Account.builder().build();
        Post post = Post.builder().id(1L).build();
        PostFavorite postFavorite = PostFavorite.builder().build();

        when(postRepository.findById(anyLong())).thenReturn(Optional.ofNullable(post));
        when(postQueryRepository.getFavoriteByPostId(post, account)).thenReturn(new ArrayList<>());

        // when, then
        PostAlreadyFavoriteOff exception =
            assertThrows(PostAlreadyFavoriteOff.class, () -> postService.deleteFavorite(post.getId(), account));
        assertEquals(PostResponseCode.POST_DELETE_FAVORITE_FAIL, exception.getResponseCode());
    }

    @DisplayName("????????? ?????? ?????? ??????")
    @Test
    void getApplysTest() {

        // given
        Account account = Account.builder().build();

        Post post1 = Post.builder().createdBy(account).content("content1").build();
        Post post2 = Post.builder().createdBy(account).content("content2").build();
        Post post3 = Post.builder().createdBy(account).content("content3").build();
        List<Post> posts = Arrays.asList(post1, post2, post3);
        final int totalPostCount = posts.size();

        when(postQueryRepository.getApplys(account)).thenReturn(posts);
        when(postQueryRepository.getJobs(any())).thenReturn(new ArrayList<>());

        // when
        List<PostResponseDTO> postResponseDTOList = postService.getApplys(account);

        // then
        assertEquals(totalPostCount, postResponseDTOList.size());
    }

    @DisplayName("?????? ?????? [??????1] - ?????? ????????? ??????")
    @Test
    void createApplyNotFoundPostException1() {

        // given
        Account account = Account.builder().build();
        Long notExistId = 12345L;
        PostApplyRequestDTO requestDTO = PostApplyRequestDTO.builder().build();
        when(postQueryRepository.getEnabledPost(notExistId)).thenThrow(
            new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // when, then
        PostNotFoundException exception =
            assertThrows(PostNotFoundException.class,
                         () -> postService.createApply(notExistId, requestDTO, account));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());

        verify(jobRepository, never()).findById(anyLong());
        verify(postQueryRepository, never()).getApplyByPostId(any(), any());
        verify(postApplyRepository, never()).save(any());
        verify(emailService, never()).sendEmail(any());

    }

    @DisplayName("?????? ?????? [??????2] - ?????? ????????? ??????")
    @Test
    void createApplyNotFoundPostException2() {

        // given
        Account account = Account.builder().build();
        Long postId = 1L;
        Post post = Post.builder().id(postId).build();

        Long notExistJobId = 12345L;
        PostApplyRequestDTO requestDTO = PostApplyRequestDTO.builder().job(notExistJobId).build();

        when(postQueryRepository.getEnabledPost(anyLong())).thenReturn(List.of(post));
        when(jobRepository.findById(notExistJobId)).thenThrow(
            new JobNotFoundException(JobResponseCode.JOB_READ_FAIL));

        // when, then
        JobNotFoundException exception =
            assertThrows(JobNotFoundException.class,
                         () -> postService.createApply(postId, requestDTO, account));
        assertEquals(JobResponseCode.JOB_READ_FAIL, exception.getResponseCode());

        verify(postQueryRepository, never()).getApplyByPostId(any(), any());
        verify(postApplyRepository, never()).save(any());
        verify(emailService, never()).sendEmail(any());

    }

    @DisplayName("?????? ?????? [??????3] - ?????? ????????? ????????? ??????")
    @Test
    void createApplyAlreadyOnException() {

        // given
        Account account = Account.builder().build();

        Post post = Post.builder().id(1L).build();
        PostApplyRequestDTO requestDTO = PostApplyRequestDTO.builder().job(1L).build();

        PostApply postApply = PostApply.builder().build();

        Job job = Job.builder().id(1L).build();

        when(postQueryRepository.getEnabledPost(anyLong())).thenReturn(List.of(post));
        when(jobRepository.findById(anyLong())).thenReturn(Optional.ofNullable(job));
        when(postQueryRepository.getApplyByPostId(post, account)).thenThrow(
            new PostAlreadyApplyOn(PostResponseCode.POST_CREATE_APPLY_FAIL));

        // when, then
        PostAlreadyApplyOn exception =
            assertThrows(PostAlreadyApplyOn.class,
                         () -> postService.createApply(post.getId(), requestDTO, account));
        assertEquals(PostResponseCode.POST_CREATE_APPLY_FAIL, exception.getResponseCode());

        verify(postApplyRepository, never()).save(any());
        verify(emailService, never()).sendEmail(any());
    }

    @DisplayName("?????? ?????? ?????? [??????1] - ?????? ????????? ??????")
    @Test
    void deleteApplyNotFoundPostException() {

        // given
        Account account = Account.builder().build();
        Long notExistId = 12345L;
        when(postQueryRepository.getEnabledPost(notExistId)).thenThrow(
            new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // when, then
        PostNotFoundException exception =
            assertThrows(PostNotFoundException.class, () -> postService.deleteApply(notExistId, account));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());

        verify(jobRepository, never()).findById(anyLong());
        verify(postQueryRepository, never()).getApplyByPostId(any(), any());
        verify(postApplyRepository, never()).save(any());
        verify(emailService, never()).sendEmail(any());

    }

    @DisplayName("?????? ?????? [??????2] - ?????? ????????? ????????? ??????")
    @Test
    void deleteApplyAlreadyOffException() {

        // given
        Account account = Account.builder().build();

        Post post = Post.builder().id(1L).build();

        Job job = Job.builder().id(1L).build();

        when(postQueryRepository.getEnabledPost(anyLong())).thenReturn(List.of(post));
        when(postQueryRepository.getApplyByPostId(post, account)).thenThrow(
            new PostAlreadyApplyOff(PostResponseCode.POST_DELETE_APPLY_FAIL));

        // when, then
        PostAlreadyApplyOff exception =
            assertThrows(PostAlreadyApplyOff.class, () -> postService.deleteApply(post.getId(), account));
        assertEquals(PostResponseCode.POST_DELETE_APPLY_FAIL, exception.getResponseCode());

        verify(postApplyRepository, never()).save(any());
        verify(emailService, never()).sendEmail(any());
    }

    @DisplayName("?????? ?????? ?????? ?????? ??????")
    @Test
    void getMyPostsTest() {

        // given
        Account account = Account.builder().id(1L).build();

        Post post1 = Post.builder().createdBy(account).content("content1").build();
        Post post2 = Post.builder().createdBy(account).content("content2").build();
        Post post3 = Post.builder().createdBy(account).content("content3").build();
        List<Post> posts = Arrays.asList(post1, post2, post3);
        final int totalPostCount = posts.size();

        when(postRepository.findAllByCreatedBy(account)).thenReturn(posts);
        when(postQueryRepository.getJobs(any())).thenReturn(new ArrayList<>());

        // when
        List<PostResponseDTO> postResponseDTOList = postService.getMyPosts(account);

        // then
        assertEquals(totalPostCount, postResponseDTOList.size());
    }

    @DisplayName("?????? ?????? ?????? [??????] - ???????????? ?????? ????????? ??????")
    @Test
    void changeRecruitingNotFoundException1() {

        // given
        Account account = Account.builder().build();
        Long notExistId = 12345L;
        PostRecruitDTO dto = PostRecruitDTO.builder().build();

        when(postRepository.findByIdAndCreatedBy(notExistId, account)).thenThrow(
            new PostNotFoundException(PostResponseCode.POST_READ_FAIL)
        );

        // when, then
        PostNotFoundException exception =
            assertThrows(PostNotFoundException.class,
                         () -> postService.changeRecruiting(notExistId, dto, account));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());
        verify(postRepository, never()).save(any());

    }
}
package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.domain.Job;
import handong.whynot.domain.Post;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.dto.job.JobResponseCode;
import handong.whynot.dto.post.PostRequestDTO;
import handong.whynot.dto.post.PostResponseCode;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.exception.account.AccountNotFoundException;
import handong.whynot.exception.job.JobNotFoundException;
import handong.whynot.exception.post.PostNotFoundException;
import handong.whynot.repository.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock private PostRepository postRepository;
    @Mock private PostQueryRepository postQueryRepository;
    @Mock private JobRepository jobRepository;
    @Mock private JobPostRepository jobPostRepository;
    @Mock private AccountRepository accountRepository;

    @InjectMocks
    private PostService postService;

    @DisplayName("공고 전체 조회 성공")
    @Test
    void getPostsTest() {

        // given, 테스트를 위한 준비(mock 객체의 행위 정의, 반환값 같은거 만들어주기 등)
        Post post1 = Post.builder().content("content1").build();
        Post post2 = Post.builder().content("content2").build();
        Post post3 = Post.builder().content("content3").build();
        List<Post> posts = Arrays.asList(post1, post2, post3);
        final int totalPostCount = posts.size();

        when(postQueryRepository.getPosts()).thenReturn(posts);
        when(postQueryRepository.getJobs(any())).thenReturn(new ArrayList<>());
        when(postQueryRepository.getApplicants(any())).thenReturn(new ArrayList<>());

        // when, 내가 테스트하고 싶은 메서드를 실행
        List<PostResponseDTO> postResponseDTOList = postService.getPosts();

        // then, 테스트 결과에 대한 검증
        assertEquals(totalPostCount, postResponseDTOList.size());
    }

    @DisplayName("공고생성 [실패1] - 등록되지 않은 사용자")
    @Test
    @Disabled
    void createPostWithAccountNotFoundException() {
        // given
        Long notExistAccountId = 123456789L;
//        PostRequestDTO requestDTO = PostRequestDTO.builder()
//                .accountId(notExistAccountId).build();
        when(accountRepository.findById(notExistAccountId))
                .thenThrow(new AccountNotFoundException(AccountResponseCode.ACCOUNT_READ_FAIL));

        // when, then
//        AccountNotFoundException exception =
//                assertThrows(AccountNotFoundException.class, () -> postService.createPost(requestDTO));
//        assertEquals(AccountResponseCode.ACCOUNT_READ_FAIL, exception.getResponseCode());
//        verify(accountRepository, times(1)).findById(notExistAccountId);
//        verify(jobRepository, never()).findById(any());
    }

    @DisplayName("공고생성 [실패2] - 등록되지 않은 직군")
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

    @DisplayName("공고 단건 조회 [실패] - 공고가 없는 경우")
    @Test
    void getPostNotFoundPostException() {

        // given
        Long notExistId = 12345L;
        Post post = Post.builder().id(notExistId).build();
        PostResponseDTO notExistResponse = PostResponseDTO.of(post, new ArrayList<>(), new ArrayList<>());
        when(postRepository.findById(notExistId)).thenThrow(new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // when, then
        PostNotFoundException exception =
                assertThrows(PostNotFoundException.class, () -> postService.getPost(notExistId));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());
    }

    @DisplayName("공고 단건 조회 성공")
    @Test
    void getPostOK() {

        // given
        Post post = Post.builder().id(1L).build();
        List<Job> jobs = new ArrayList<Job>();
        List<Account> applicants = new ArrayList<Account>();

        PostResponseDTO expectedResponse = PostResponseDTO.of(post, jobs, applicants);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(postQueryRepository.getJobs(post.getId())).thenReturn(jobs);
        when(postQueryRepository.getApplicants(post.getId())).thenReturn(applicants);

        // when
        PostResponseDTO actualResponse = postService.getPost(post.getId());

        // then
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

    @DisplayName("공고 단건 삭제 [실패1] - 본인 공고가 아닌 경우")
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

    @DisplayName("공고 단건 삭제 [실패2] - 본인 공고이나 존재하지 않는 공고인 경우")
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
                assertThrows(PostNotFoundException.class, () -> postService.deletePost(notExistPostId, currentAccount));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());
        verify(jobPostRepository, never()).findAllByPost(any());
    }

    @DisplayName("공고 단건 업데이트 [실패1] - 본인 공고가 아닌 경우")
    @Test
    void updatePostNotFoundException1() {

        // given
        Long currentAccountId = 1L;
        Long postId = 1L;

        Account currentAccount = Account.builder().id(currentAccountId).build();

        PostRequestDTO dto = PostRequestDTO.builder()
                .title("제목 수정")
                .content("내용 수정")
                .postImg("http://image-edited.com")
                .build();

        when(postRepository.findByIdAndCreatedBy(postId, currentAccount))
                .thenThrow(new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // when, then
        PostNotFoundException exception =
                assertThrows(PostNotFoundException.class, () -> postService.updatePost(postId, dto, currentAccount));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());
        verify(postRepository, never()).save(any());

    }

    @DisplayName("공고 단건 업데이트 [실패2] - 본인 공고이나 존재하지 않는 공고인 경우")
    @Test
    void updatePostNotFoundException2() {

        // given
        Long currentAccountId = 1L;
        Long notExistPostId = 12345L;

        Account currentAccount = Account.builder().id(currentAccountId).build();

        PostRequestDTO dto = PostRequestDTO.builder()
                .title("제목 수정")
                .content("내용 수정")
                .postImg("http://image-edited.com")
                .build();
        when(postRepository.findByIdAndCreatedBy(notExistPostId, currentAccount))
                .thenThrow(new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // when, then
        PostNotFoundException exception =
                assertThrows(PostNotFoundException.class, () -> postService.updatePost(notExistPostId, dto, currentAccount));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());
        verify(postRepository, never()).save(any());
    }
}
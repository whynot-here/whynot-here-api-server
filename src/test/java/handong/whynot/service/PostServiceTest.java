package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.domain.Job;
import handong.whynot.domain.Post;
import handong.whynot.dto.job.JobResponseCode;
import handong.whynot.dto.post.PostRequestDTO;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.dto.account.AccountResponseCode;
import handong.whynot.exception.job.JobNotFoundException;
import handong.whynot.exception.account.AccountNotFoundException;
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

    @DisplayName("공고 전체 조회 테스트")
    @Test
    void getPosts() {

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
}
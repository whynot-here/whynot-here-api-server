package handong.whynot.service;

import handong.whynot.domain.Post;
import handong.whynot.dto.post.PostRequestDTO;
import handong.whynot.dto.post.PostResponseDTO;
import handong.whynot.dto.user.UserResponseCode;
import handong.whynot.exception.user.UserNotFoundException;
import handong.whynot.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    private UserRepository userRepository;
    @InjectMocks
    private PostService postService;

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

    @Test
    void createPostWithException() {
        // given
        PostRequestDTO requestDTO = PostRequestDTO.builder().build();
        when(userRepository.findById(any())).thenThrow(new UserNotFoundException(UserResponseCode.USER_READ_FAIL));

        // when, then
        UserNotFoundException exception =
                assertThrows(UserNotFoundException.class, () -> postService.createPost(requestDTO));
        assertEquals(UserResponseCode.USER_READ_FAIL, exception.getResponseCode());
        verify(userRepository, times(1)).findById(any());
        verify(postRepository, never()).save(any());
    }
}
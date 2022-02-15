package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.domain.Comment;
import handong.whynot.domain.Post;
import handong.whynot.dto.comment.CommentResponseDTO;
import handong.whynot.dto.post.PostResponseCode;
import handong.whynot.exception.post.PostNotFoundException;
import handong.whynot.repository.CommentQueryRepository;
import handong.whynot.repository.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @Mock private CommentQueryRepository commentQueryRepository;
    @Mock private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService;

    @DisplayName("공고에 대한 댓글 조회 [실패] - 존재하지 않는 공고인 경우")
    @Test
    void getCommentsNotFoundPostException() {

        // given
        Long notExistId = 12345L;
        when(postRepository.findById(notExistId)).thenThrow(new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // when, then
        PostNotFoundException exception =
                assertThrows(PostNotFoundException.class, () -> commentService.getCommentsByPostId(notExistId));
        assertEquals(PostResponseCode.POST_READ_FAIL, exception.getResponseCode());
        verify(commentQueryRepository, never()).findCommentsByPostId(anyLong());
    }

    @DisplayName("공고에 대한 댓글 조회 [성공]")
    @Test
    void getCommentsTest() {

        // given
        Long postId = 1L;
        Post post = Post.builder().id(postId).build();
        Account account = Account.builder().nickname("whynot-user").build();

        Comment comment1 = Comment.builder().post(post).createdBy(account).build();
        Comment comment2 = Comment.builder().post(post).createdBy(account).build();
        Comment comment3 = Comment.builder().post(post).createdBy(account).build();
        comment1.setParent(comment1);
        comment2.setParent(comment2);
        comment3.setParent(comment3);

        List<Comment> comments = Arrays.asList(comment1, comment2, comment3);
        final int totalPostCount = comments.size();

        when(postRepository.findById(postId)).thenReturn(Optional.ofNullable(post));
        when(commentQueryRepository.findCommentsByPostId(postId)).thenReturn(comments);

        // when
        List<CommentResponseDTO> actualResponse = commentService.getCommentsByPostId(postId);

        // then
        assertEquals(totalPostCount, actualResponse.size());

    }
}

package handong.whynot.service;

import handong.whynot.domain.Account;
import handong.whynot.domain.Comment;
import handong.whynot.domain.Post;
import handong.whynot.dto.comment.CommentRequestDTO;
import handong.whynot.dto.comment.CommentResponseCode;
import handong.whynot.dto.comment.CommentResponseDTO;
import handong.whynot.dto.post.PostResponseCode;
import handong.whynot.exception.comment.CommentNotFoundException;
import handong.whynot.exception.post.PostNotFoundException;
import handong.whynot.repository.CommentQueryRepository;
import handong.whynot.repository.CommentRepository;
import handong.whynot.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;
    private final PostRepository postRepository;

    public List<CommentResponseDTO> getCommentsByPostId(Long postId) {

        // 존재하는 post인지 검증
        postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        List<Comment> comments = commentQueryRepository.findCommentsByPostId(postId);
        return comments.stream()
                .map(CommentResponseDTO::of)
                .collect(Collectors.toList());
    }

    public void createComment(CommentRequestDTO requestDTO, Account account) {

        // 존재하는 post인지 검증
        Post entity = postRepository.findById(requestDTO.getPostId())
                .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        // 댓글 생성
        Comment comment = Comment.builder()
                .post(entity)
                .content(requestDTO.getComment())
                .createdBy(account)
                .build();

        commentRepository.save(comment);
    }

    public void deleteComment(Long commentId, Account account) {

        // 본인 comment가 존재하는지 검증
        Comment comment = commentQueryRepository.findCommentsByAccount(commentId, account)
                .orElseThrow(() -> new CommentNotFoundException(CommentResponseCode.COMMENT_DELETE_FAIL_NOT_FOUND));

        // 댓글 삭제
        commentRepository.delete(comment);
    }

    public void deleteCommentsByPostId(Post post) {

        List<Comment> comments = commentRepository.findAllByPostId(post.getId());
        comments.forEach(comment -> commentRepository.deleteById(comment.getId()));
    }
}

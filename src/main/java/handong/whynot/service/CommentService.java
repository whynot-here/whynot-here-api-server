package handong.whynot.service;

import handong.whynot.domain.Comment;
import handong.whynot.dto.comment.CommentResponseDTO;
import handong.whynot.dto.post.PostResponseCode;
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

        postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(PostResponseCode.POST_READ_FAIL));

        List<Comment> comments = commentQueryRepository.findCommentsByPostId(postId);
        return comments.stream()
                .map(CommentResponseDTO::of)
                .collect(Collectors.toList());
    }
}

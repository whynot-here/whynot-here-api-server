package handong.whynot.api;

import handong.whynot.domain.Comment;
import handong.whynot.dto.comment.CommentResponseDTO;
import handong.whynot.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "공고에 대한 댓글 조회")
    @GetMapping("/{postId}")
    public List<CommentResponseDTO> getCommentsByPostId(@PathVariable Long postId) {

        return commentService.getCommentsByPostId(postId);
    }
}

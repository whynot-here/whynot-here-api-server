package handong.whynot.api;

import handong.whynot.domain.Account;
import handong.whynot.domain.Comment;
import handong.whynot.dto.account.CurrentAccount;
import handong.whynot.dto.comment.CommentRequestDTO;
import handong.whynot.dto.comment.CommentResponseCode;
import handong.whynot.dto.comment.CommentResponseDTO;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("")
    public ResponseDTO createComment(@RequestBody CommentRequestDTO requestDTO, @CurrentAccount Account account) {

        commentService.createComment(requestDTO, account);

        return ResponseDTO.of(CommentResponseCode.COMMENT_CREATE_OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseDTO deleteComment(@PathVariable Long commentId, @CurrentAccount Account account) {

        commentService.deleteComment(commentId, account);

        return ResponseDTO.of(CommentResponseCode.COMMENT_DELETE_OK);

    }
}

package handong.whynot.api.v2;

import handong.whynot.domain.Account;
import handong.whynot.dto.comment.CommentRequestDTO;
import handong.whynot.dto.comment.CommentResponseCode;
import handong.whynot.dto.comment.CommentResponseDTO;
import handong.whynot.dto.common.ResponseDTO;
import handong.whynot.service.AccountService;
import handong.whynot.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/comments")
@RequiredArgsConstructor
public class CommentControllerV2 {

    private final CommentService commentService;
    private final AccountService accountService;

    @Operation(summary = "공고에 대한 댓글 조회")
    @GetMapping("/{postId}")
    public List<CommentResponseDTO> getCommentsByPostId(@PathVariable Long postId) {

        return commentService.getCommentsByPostId(postId);
    }

    @PostMapping("")
    public ResponseDTO createComment(@RequestBody CommentRequestDTO requestDTO) {

        Account account = accountService.getCurrentAccount();
        commentService.createComment(requestDTO, account);

        return ResponseDTO.of(CommentResponseCode.COMMENT_CREATE_OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseDTO deleteComment(@PathVariable Long commentId) {

        Account account = accountService.getCurrentAccount();
        commentService.deleteComment(commentId, account);

        return ResponseDTO.of(CommentResponseCode.COMMENT_DELETE_OK);

    }
}

package handong.whynot.dto.comment;

import handong.whynot.domain.Comment;
import handong.whynot.dto.account.AccountResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {

    private Long commentId;
    private String content;
    private AccountResponseDTO account;
    private LocalDateTime createdDt;

    public static CommentResponseDTO of(Comment comment) {

        return CommentResponseDTO.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .account(AccountResponseDTO.of(comment.getCreatedBy()))
                .createdDt(comment.getCreatedDt())
                .build();
    }

}

package handong.whynot.dto.comment;

import handong.whynot.domain.Comment;
import handong.whynot.dto.post.PostWriterDTO;
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
    private PostWriterDTO account;
    private LocalDateTime createdDt;

    public static CommentResponseDTO of(Comment comment) {

        return builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .account(PostWriterDTO.of(comment.getCreatedBy()))
                .createdDt(comment.getCreatedDt())
                .build();
    }
}

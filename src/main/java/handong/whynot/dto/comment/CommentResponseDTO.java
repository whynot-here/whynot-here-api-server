package handong.whynot.dto.comment;

import handong.whynot.domain.Comment;
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
    private String writer;
    private LocalDateTime createdDt;

    public static CommentResponseDTO of(Comment comment) {
        return CommentResponseDTO.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .writer(comment.getCreatedBy().getNickname())
                .createdDt(comment.getCreatedDt())
                .build();
    }

}

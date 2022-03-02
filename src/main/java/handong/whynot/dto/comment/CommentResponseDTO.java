package handong.whynot.dto.comment;

import handong.whynot.domain.Comment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CommentResponseDTO {

    private Long postId;
    private Long parentId;
    private String content;
    private String writer;

    public static CommentResponseDTO of(Comment comment) {
        return CommentResponseDTO.builder()
                .postId(comment.getPost().getId())
                .parentId(comment.getParent().getId())
                .content(comment.getContent())
                .writer(comment.getCreatedBy().getNickname())
                .build();
    }

}

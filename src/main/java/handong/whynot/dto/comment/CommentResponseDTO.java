package handong.whynot.dto.comment;

import handong.whynot.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDTO {

    private Long postId;
    private Long parentId;
    private String content;
    private String writer;

    public static CommentResponseDTO of(Comment comment) {
        return builder().postId(comment.getPost().getId())
                        .parentId(comment.getParent().getId())
                        .content(comment.getContent())
                        .writer(comment.getCreatedBy().getNickname())
                        .build();
    }
}

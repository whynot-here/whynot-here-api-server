package handong.whynot.dto.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    private Long commentId;
    private String content;
    private PostWriterDTO account;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
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

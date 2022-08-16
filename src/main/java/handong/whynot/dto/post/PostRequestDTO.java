package handong.whynot.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDTO {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    private String title;
    private String content;
    private String postImg;
    private Long categoryId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime closedDt;

    private ContactType ownerContact;
    private Integer recruitTotalCnt;
    private Integer recruitCurrentCnt;
    private CommunicationType communicationTool;

}

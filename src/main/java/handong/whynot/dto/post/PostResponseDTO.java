package handong.whynot.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import handong.whynot.domain.Job;
import handong.whynot.domain.Post;
import handong.whynot.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostResponseDTO {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    @JsonProperty(namespace = "postId")
    private Long id;

    private String title;

    private String postImg;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime createdDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime updatedDt;

    private User createdBy;

    private String content;

    private boolean isRecruiting;

    @Builder.Default
    private List<Job> jobs = new ArrayList<Job>();

    @Builder.Default
    private List<User> applicants = new ArrayList<User>();

    public static PostResponseDTO of(Post post, List<Job> jobs, List<User> applicants) {
        return builder()
                .id(post.getId())
                .title(post.getTitle())
                .postImg(post.getPostImg())
                .createdDt(post.getCreatedDt())
                .updatedDt(post.getUpdatedDt())
                .createdBy(post.getCreatedBy())
                .content(post.getContent())
                .isRecruiting(post.isRecruiting())
                .jobs(jobs)
                .applicants(applicants)
                .build();
    }
}

package handong.whynot.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import handong.whynot.domain.Job;
import handong.whynot.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
public class PostResponseDTO {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    @JsonProperty(namespace = "postId")
    public Long id;

    public String title;

    public String postImg;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    public LocalDateTime createdDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    public LocalDateTime updatedDt;

    public User createdBy;

    public String content;

    public boolean isRecruiting;

    public List<Job> jobs = new ArrayList<Job>();

    public List<User> applicants = new ArrayList<User>();
}

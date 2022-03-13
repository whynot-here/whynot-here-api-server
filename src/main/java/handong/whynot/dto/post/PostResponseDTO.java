package handong.whynot.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import handong.whynot.domain.Account;
import handong.whynot.domain.Job;
import handong.whynot.domain.Post;
import handong.whynot.dto.account.AccountResponseDTO;
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

    private AccountResponseDTO owner;

    private String content;

    private boolean isRecruiting;

    @Builder.Default
    private List<Job> jobs = new ArrayList<>();

    @Builder.Default
    private List<AccountResponseDTO> applicants = new ArrayList<>();

    public static PostResponseDTO of(Post post, List<Job> jobs, List<AccountResponseDTO> applicants) {
        return builder()
                .id(post.getId())
                .title(post.getTitle())
                .postImg(post.getPostImg())
                .createdDt(post.getCreatedDt())
                .updatedDt(post.getUpdatedDt())
                .owner(post.getCreatedBy().getAccountDTO())
                .content(post.getContent())
                .isRecruiting(post.isRecruiting())
                .jobs(jobs)
                .applicants(applicants)
                .build();
    }
}

package handong.whynot.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import handong.whynot.domain.Job;
import handong.whynot.domain.Post;
import handong.whynot.dto.account.AccountResponseDTO;
import handong.whynot.dto.category.CategoryDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm";

    @JsonProperty(namespace = "postId")
    private Long id;

    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime createdDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime updatedDt;

    private PostWriterDTO writer;

    private String content;

    private boolean isRecruiting;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    private LocalDateTime closedDt;

    private OwnerContact ownerContact;

    private Integer recruitTotalCnt;

    private Integer recruitCurrentCnt;

    private CommunicationType communicationTool;

    private CategoryDTO category;

    private List<PostImageLinkDTO> imageLinks = new ArrayList<>();

    public static PostResponseDTO of(Post post, List<Job> jobs, List<AccountResponseDTO> applicants) {
        return builder()
            .id(post.getId())
            .title(post.getTitle())
            .createdDt(post.getCreatedDt())
            .updatedDt(post.getUpdatedDt())
                .writer(PostWriterDTO.of(post.getCreatedBy()))
            .content(post.getContent())
            .isRecruiting(post.isRecruiting())
            .build();
    }

    public static PostResponseDTO of(Post post) {

        List<PostImageLinkDTO> imageLinks = post.getLinks().stream()
                .map(it -> PostImageLinkDTO.of(it.getId(), it.getLink()))
                .collect(Collectors.toList());

        return builder()
                .id(post.getId())
                .title(post.getTitle())
                .createdDt(post.getCreatedDt())
                .updatedDt(post.getUpdatedDt())
                .writer(PostWriterDTO.of(post.getCreatedBy()))
                .content(post.getContent())
                .isRecruiting(post.isRecruiting())
                .closedDt(post.getClosedDt())
                .ownerContact(new OwnerContact(post.getOwnerContactType(), post.getOwnerContactValue()))
                .recruitTotalCnt(post.getRecruitTotalCnt())
                .recruitCurrentCnt(post.getRecruitCurrentCnt())
                .communicationTool(post.getCommunicationTool())
                .category(CategoryDTO.generateDTOBy(post.getCategoryId()))
                .imageLinks(imageLinks)
                .build();
    }
}

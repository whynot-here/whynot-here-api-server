package handong.whynot.dto.post;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import handong.whynot.domain.Post;
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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedDt;

    private PostWriterDTO writer;

    private String content;

    private boolean isRecruiting;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_PATTERN, timezone = "Asia/Seoul")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime closedDt;

    private OwnerContact ownerContact;

    private Integer recruitTotalCnt;

    private Integer recruitCurrentCnt;

    private CommunicationType communicationTool;

    private CategoryDTO category;

    private Integer views;

    private Integer likes;

    private String locationUrl;

    private List<PostImageLinkDTO> imageLinks = new ArrayList<>();

    private int commentCnt;

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
                .views(post.getViews())
                .likes(post.getLikes())
                .locationUrl(post.getLocationUrl())
                .imageLinks(imageLinks)
                .commentCnt(post.getCommentCnt())
                .build();
    }
}

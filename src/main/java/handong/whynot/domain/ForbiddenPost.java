package handong.whynot.domain;

import handong.whynot.domain.common.BaseTimeEntity;
import handong.whynot.dto.post.CommunicationType;
import handong.whynot.dto.post.ContactType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ForbiddenPost extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "is_recruiting")
    private Boolean isRecruiting;

    @Column(name = "closed_dt")
    private LocalDateTime closedDt;

    @Column(name = "owner_contact_type")
    @Enumerated(EnumType.STRING)
    private ContactType ownerContactType;

    @Column(name = "owner_contact_value")
    private String ownerContactValue;

    @Column(name = "recruit_total_cnt")
    private Integer recruitTotalCnt;

    @Column(name = "recruit_current_cnt")
    private Integer recruitCurrentCnt;

    @Column(name = "communication_tool")
    @Enumerated(EnumType.STRING)
    private CommunicationType communicationTool;

    @Column(name = "views")
    private Integer views;

    @Column(name = "likes")
    private Integer likes;

    @Column(name = "location_url")
    private String locationUrl;

    @Column(name = "post_img_links")
    private String postImgLinks;

    @Column(name = "comments")
    private String comments;

    public static ForbiddenPost of(Post post, List<Comment> comments) {
        return builder()
          .accountId(post.getCreatedBy().getId())
          .categoryId(post.getCategoryId().getId())
          .title(post.getTitle())
          .content(post.getContent())
          .isRecruiting(post.isRecruiting())
          .closedDt(post.getClosedDt())
          .ownerContactType(post.getOwnerContactType())
          .ownerContactValue(post.getOwnerContactValue())
          .recruitTotalCnt(post.getRecruitTotalCnt())
          .recruitCurrentCnt(post.getRecruitCurrentCnt())
          .communicationTool(post.getCommunicationTool())
          .views(post.getViews())
          .likes(post.getLikes())
          .locationUrl(post.getLocationUrl())
          .postImgLinks(post.getLinks().stream().map(PostImageLink::getLink).collect(Collectors.toList()).toString())
          .comments(comments.stream().map(Comment::getContent).collect(Collectors.toList()).toString())
          .build();
    }
}

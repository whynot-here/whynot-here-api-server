package handong.whynot.domain;

import handong.whynot.domain.common.BaseTimeEntity;
import handong.whynot.dto.post.CommunicationType;
import handong.whynot.dto.post.ContactType;
import handong.whynot.dto.post.PostRequestDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account createdBy;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "is_recruiting")
    private boolean isRecruiting;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImageLink> links = new ArrayList<>();

    @Builder
    public Post(Account createdBy, String title, String content) {
        this.createdBy = createdBy;
        this.title = title;
        this.content = content;
        isRecruiting = true;
    }

    public void addLinks(List<PostImageLink> links) {
        this.links = links;
    }

    public void update(PostRequestDTO request, Category category, List<PostImageLink> imageLinks) {
        title = request.getTitle();
        content = request.getContent();
        categoryId = category;
        closedDt = request.getClosedDt();
        ownerContactType = request.getOwnerContact().getType();
        ownerContactValue = request.getOwnerContact().getValue();
        recruitTotalCnt = request.getRecruitTotalCnt();
        recruitCurrentCnt = request.getRecruitCurrentCnt();
        communicationTool = request.getCommunicationTool();
        links.clear();
        links.addAll(imageLinks);
    }

    public void increaseViews() {
        views += 1;
    }
}

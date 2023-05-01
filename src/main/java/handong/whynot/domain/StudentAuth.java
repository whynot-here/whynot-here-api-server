package handong.whynot.domain;

import handong.whynot.domain.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class StudentAuth extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name="img_url")
    private String imgUrl;

    @Column(name="is_authenticated")
    private Boolean isAuthenticated;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "approver")
    private String approver;

    public void updateImageUrl(String url) {
        this.imgUrl = url;
    }

    public void updateIsAuthenticated(String approver) {
        this.isAuthenticated = true;
        this.approvedAt = LocalDateTime.now();
        this.approver = approver;
    }
}

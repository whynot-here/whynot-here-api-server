package handong.whynot.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class StudentAuth {

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
    private boolean isAuthenticated;

    public void updateImageUrl(String url) {
        this.imgUrl = url;
    }
}

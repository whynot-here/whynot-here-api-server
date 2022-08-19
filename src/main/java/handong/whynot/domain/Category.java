package handong.whynot.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order")
    private Integer order;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "is_leaf")
    private String isLeaf;

    @Column(name = "parent_code")
    private String parentCode;

    @Column(name = "use_yn")
    private String useYn;
}

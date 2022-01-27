package handong.whynot.domain;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@EqualsAndHashCode(of = "id")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "email")
    public String email;

    @Column(name = "nickname")
    public String nickname;

    @Column(name = "password")
    public String password;

    @Column(name = "profile_img")
    public String profileImg;



}

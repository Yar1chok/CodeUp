package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "gamer")
public class Gamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    public Long idUser;

    @Column(name = "login")
    public String login;

    @Column(name = "password")
    public String password;
    @Column(name = "cur_lvl")
    public Integer curLvl;
}

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
    @Column(name = "cur_lvl_java")
    public Integer curLvlJava;

    public Gamer() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCurLvlJava() {
        return curLvlJava;
    }

    public void setCurLvlJava(Integer curLvlJava) {
        this.curLvlJava = curLvlJava;
    }

    public Gamer(String login, String password) {
        this.login = login;
        this.password = password;
        this.curLvlJava = 1;
    }
}

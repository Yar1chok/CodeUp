package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "gamer")
public class Gamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    public Long idUser;

    @Column(name = "email")
    public String email;

    @Column(name = "user_name")
    public String userName;
    @Column(name = "password")
    public String password;
    @Column(name = "cur_lvl_java")
    public Integer curLvlJava;

    public Gamer() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Gamer(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.curLvlJava = 1;
        this.userName = userName;
    }
}

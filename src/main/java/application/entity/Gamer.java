package application.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "gamer")
public class Gamer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    public Long idUser;

    @Column(name = "email", unique = true)
    public String email;

    @Column(name = "user_name")
    public String nickname;
    @Column(name = "password")
    public String password;
    @Column(name = "cur_lvl_java", columnDefinition = "INTEGER DEFAULT 1")
    public Integer curLvlJava;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Gamer() {
    }

    public String getNickname(){
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public Gamer(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.curLvlJava = 1;
        this.nickname = nickname;
    }
}

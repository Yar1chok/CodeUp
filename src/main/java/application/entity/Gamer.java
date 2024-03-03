package application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "gamer")
public class Gamer implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "user_name", nullable = false)
    private String nickname;
    @Column(name = "password")
    private String password;
    @Column(name = "cur_lvl_java", columnDefinition = "INTEGER DEFAULT 1")
    private Integer curLvlJava;
    @Column(name = "block_java", columnDefinition = "INTEGER DEFAULT 1")
    private Integer blockJava;

    @Column(name = "birthday")
    private String birthday;


    @Column(name = "name")
    private String name;

    public String getGithub() {
        return github;
    }


    public void setGithub(String github) {
        this.github = github;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    @Column(name = "github")
    private String github;

    @Column(name = "image")
    private byte[] image;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getBlockJava() {
        return blockJava;
    }

    public void setBlockJava(Integer blockJava) {
        this.blockJava = blockJava;
    }

    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "gamer_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Gamer() {
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
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
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
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

    public Gamer(String email, String password, String nickname, String name) {
        this.email = email;
        this.password = password;
        this.curLvlJava = 1;
        this.blockJava = 1;
        this.nickname = nickname;
        this.name = name;
    }
}

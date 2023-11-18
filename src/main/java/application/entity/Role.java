package application.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String role;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<Gamer> gamers;
    public Role() {
    }

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Gamer> getGamers() {
        return gamers;
    }

    public void setGamers(Set<Gamer> gamers) {
        this.gamers = gamers;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }
}

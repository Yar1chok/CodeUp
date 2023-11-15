package service;

import entity.Gamer;
import entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.GamerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GamerService implements UserDetailsService {

    @Autowired
    private GamerRepository gamerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Gamer gamer = gamerRepository.findGamerByEmail(email);

        if (gamer == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(gamer.getEmail())
                .password(gamer.getPassword())
                .roles("USER")
                .build();
    }

    public Gamer findUserById(Long id) {
        Optional<Gamer> gamerFromDb = gamerRepository.findById(id);
        return gamerFromDb.orElse(new Gamer());
    }

    public List<Gamer> allGamers() {
        return gamerRepository.findAll();
    }

    public boolean saveGamer(Gamer gamer) {
        Gamer gamerFromDB = gamerRepository.findGamerByEmail(gamer.getEmail());

        if (gamerFromDB != null) {
            return false;
        }

        gamer.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        gamer.setPassword(passwordEncoder.encode(gamer.getPassword()));
        gamerRepository.save(gamer);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (gamerRepository.findById(userId).isPresent()) {
            gamerRepository.deleteById(userId);
            return true;
        }
        return false;
    }

    public boolean loginGamer(String email, String password) {
        Gamer gamer = gamerRepository.findGamerByEmail(email);
        return gamer != null && passwordEncoder.matches(password, gamer.getPassword());
    }
}

package application.service;

import application.entity.Gamer;
import application.repository.GamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GamerDetailsService implements UserDetailsService {
    private final GamerRepository gamerRepository;

    @Autowired
    public GamerDetailsService(GamerRepository gamerRepository) {
        this.gamerRepository = gamerRepository;
    }

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

}

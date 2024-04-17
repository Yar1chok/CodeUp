package application.service;

import application.entity.Gamer;
import application.repository.GamerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class GamerDetailsService implements UserDetailsService {
    private final GamerRepository gamerRepository;
    private final CipherService cipherService;
    @Autowired
    public GamerDetailsService(GamerRepository gamerRepository, CipherService cipherService) {
        this.gamerRepository = gamerRepository;
        this.cipherService = cipherService;
    }

    @Override
    public UserDetails loadUserByUsername(String encryptedEmail) throws UsernameNotFoundException {
        String email = cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedEmail)));
        Gamer gamer = gamerRepository.findGamerByEmail(email);
        if (gamer == null || !gamer.isEmailVerified()) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(gamer.getEmail())
                .password(gamer.getPassword())
                .roles("USER")
                .build();
    }

}

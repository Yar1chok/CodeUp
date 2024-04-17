package application.service;

import application.entity.Gamer;
import application.entity.Role;
import application.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import application.repository.GamerRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class GamerService {

    private final GamerRepository gamerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public GamerService(GamerRepository gamerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.gamerRepository = gamerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Gamer findGamerById(Long id) {
        Optional<Gamer> gamerFromDb = gamerRepository.findById(id);
        return gamerFromDb.orElse(new Gamer());
    }

    public Gamer findGamerByEmail(String email) {
        return gamerRepository.findGamerByEmail(email);
    }

    public List<Gamer> allGamers() {
        return gamerRepository.findAll();
    }

    public boolean saveGamer(Gamer gamer) {
        Gamer gamerFromDB = gamerRepository.findGamerByEmail(gamer.getEmail());
        if (gamerFromDB != null) {
            return false;
        }
        gamer.setCurLvlJava(1);
        gamer.setBlockJava(1);
        Set<Role> roleSet = new HashSet<>();
        Role gamerRole = roleRepository.getRoleById(1L);
        roleSet.add(gamerRole);
        gamer.setRoles(roleSet);
        gamer.setPassword(passwordEncoder.encode(gamer.getPassword()));
        gamerRepository.save(gamer);
        return true;
    }

    public void justUpdate(Gamer gamer){
        gamerRepository.save(gamer);
    }
    public boolean updateGamer(Gamer gamer, Long id, MultipartFile image, String birthday) {
        Optional<Gamer> gamerFromDB = gamerRepository.findById(id);
        if (gamerFromDB.isPresent()) {
            try (InputStream stream = image.getInputStream()) {
                byte[] bytes = stream.readAllBytes();
                if (bytes.length != 0) {
                    gamer.setImage(bytes);
                } else {
                    gamer.setImage(gamerFromDB.get().getImage());
                }
            } catch (IOException e) {
                return false;
            }
            gamer.setBirthday(birthday);
            gamer.setIdUser(id);
            gamer.setEmail(gamerFromDB.get().getEmail());
            gamer.setPassword(gamerFromDB.get().getPassword());
            gamer.setCurLvlJava(gamerFromDB.get().getCurLvlJava());
            gamer.setBlockJava(gamerFromDB.get().getBlockJava());
            gamerRepository.save(gamer);
            return true;
        } else {
            return false;
        }
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

    public boolean checkVerification(String email){
        Gamer gamer = gamerRepository.findGamerByEmail(email);
        return gamer != null && gamer.isEmailVerified();
    }

    public boolean activateEmail(String email){
        try {
            Gamer gamer = gamerRepository.findGamerByEmail(email);
            gamer.setEmailVerified(true);
            gamerRepository.save(gamer);
            return true;
        } catch (Exception exception){
            return false;
        }
    }

    public Gamer findGamerByVerificationToken(String token){
        return this.gamerRepository.findGamerByVerificationToken(token);
    }
}

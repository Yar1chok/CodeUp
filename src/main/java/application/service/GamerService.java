package application.service;

import application.entity.Gamer;
import application.entity.Role;
import application.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import application.repository.GamerRepository;

import java.util.*;

@Service
public class GamerService {

    @Autowired
    private GamerRepository gamerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        Set<Role> roleSet = new HashSet<>();
        Role gamerRole = roleRepository.getRoleById(1L);
        roleSet.add(gamerRole);
        gamer.setRoles(roleSet);
        gamer.setAge(1);
        gamer.setPassword(passwordEncoder.encode(gamer.getPassword()));
        gamerRepository.save(gamer);
        return true;
    }

    public boolean updateGamer(Gamer gamer, Long id) {
        Optional<Gamer> gamerFromDB = gamerRepository.findById(id);
        if (gamerFromDB.isPresent()) {
            gamer.setIdUser(id);
            gamer.setEmail(gamerFromDB.get().getEmail());
            gamer.setPassword(gamerFromDB.get().getPassword());
            gamer.setCurLvlJava(gamerFromDB.get().getCurLvlJava());
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
}

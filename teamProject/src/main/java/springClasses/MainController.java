package springClasses;

import entity.Gamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repository.GamerRepository;
import org.springframework.ui.Model;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class MainController {
    private static final Logger LOG = Logger.getLogger(String.valueOf(MainController.class));

    private final GamerRepository gamerRepository;

    @Autowired
    public MainController(GamerRepository gamerRepository) {
        this.gamerRepository = gamerRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam(value = "email", required = false) String email,
                                @RequestParam(value = "password", required = false) String password,
                                @RequestParam(value = "user_name", required = false) String userName,
                                Model model) {
        List<Gamer> existingUser = gamerRepository.findGamerByEmail(email);
        if (!existingUser.isEmpty()) {
            model.addAttribute("errorMessage",
                    "Пользователь с таким логином уже существует");
            return "registration";
        } else {
            Gamer gamer = new Gamer(email, password, userName);
            gamerRepository.save(gamer);
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "email", required = false) String email,
                               @RequestParam(value = "password", required = false) String password,
                               Model model) {
        List<Gamer> existingUser = gamerRepository.findGamerByEmail(email);
        if (!existingUser.isEmpty() && existingUser.get(0).getEmail().equals(email)
                && existingUser.get(0).getPassword().equals(password)) {
            model.addAttribute("user", existingUser.get(0));
            return "redirect:/menu";
        } else {
            model.addAttribute("errorMessage",
                    "Неправильный логин или пароль");
            return "login";
        }
    }
    @GetMapping("/menu")
    public String menu() {
        return "menu";
    }
}

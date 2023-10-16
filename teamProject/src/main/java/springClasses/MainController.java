package springClasses;

import entity.Gamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repository.GamerRepository;
import org.springframework.ui.Model;
import java.util.List;

@Controller
public class MainController {

    private final GamerRepository gamerRepository;

    @Autowired
    public MainController(GamerRepository gamerRepository) {
        this.gamerRepository = gamerRepository;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@RequestParam(value = "login", required = false) String login,
                                @RequestParam(value = "password", required = false) String password,
                                Model model) {
        List<Gamer> existingUser = gamerRepository.findGamerByLogin(login);
        if (!existingUser.isEmpty()) {
            model.addAttribute("errorMessage",
                    "Пользователь с таким логином уже существует");
            return "registration";
        } else {
            Gamer gamer = new Gamer(login, password);
            gamerRepository.save(gamer);
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(value = "login", required = false) String login,
                               @RequestParam(value = "password", required = false) String password,
                               Model model) {
        List<Gamer> existingUser = gamerRepository.findGamerByLogin(login);
        if (!existingUser.isEmpty() && existingUser.get(0).getLogin().equals(login)
                && existingUser.get(0).getPassword().equals(password)) {
            model.addAttribute("user", existingUser.get(0));
            return "redirect:/menu";
        } else {
            model.addAttribute("errorMessage",
                    "Неправильный логин или пароль");
            return "login";
        }
    }
}

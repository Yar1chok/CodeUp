package application.controller;

import application.entity.Gamer;
import application.service.GamerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
@RequestMapping("/CodeUp")
public class MenuController {

    private final GamerService gamerService;

    @Autowired
    public MenuController(GamerService gamerService) {
        this.gamerService = gamerService;
    }

    @GetMapping("/menu")
    public String menuGet(){
        return "menu";
    }

    @GetMapping("/profile")
    public RedirectView profileGet(Principal principal, Model model) {
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);

        if (gamer != null) {
            model.addAttribute("gamer", gamer);
            return new RedirectView("/CodeUp/profile/" + gamer.getIdUser(), true);
        } else {
            return new RedirectView("/CodeUp/menu", true);
        }
    }

    @GetMapping("/profile/{id}")
    public String profileById(@PathVariable Long id, Model model) {
        Gamer gamer = gamerService.findGamerById(id);

        if (gamer != null) {
            model.addAttribute("gamer", gamer);
            return "profile";
        } else {
            return "redirect:/CodeUp/menu";
        }
    }
}

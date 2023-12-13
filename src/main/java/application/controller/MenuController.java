package application.controller;

import application.entity.Gamer;
import application.service.GamerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

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

    @GetMapping("/settings")
    public String settingsGet(Principal principal){
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);
        if (gamer != null) {
            return "redirect:/CodeUp/settings/" + gamer.getIdUser();
        } else {
            return "menu";
        }
    }

    @GetMapping("/settings/{id}")
    public String settingsIdGet(Model model, @PathVariable String id){
        Gamer gamer = gamerService.findGamerById(Long.parseLong(id));
        model.addAttribute("gamer", gamer);
        return "settings";
    }
    @PostMapping("/settings/{id}")
    public String settingsPost(@PathVariable String id, @ModelAttribute @Valid Gamer gamer){
        if (gamerService.updateGamer(gamer, Long.parseLong(id))) {
            return "redirect:/CodeUp/profile?error=true";
        } else {
            return "redirect:/CodeUp/profile";
        }
    }

    @GetMapping("/level")
    public String levelGet(){
        return "level";
    }

    @GetMapping("/javaTower")
    public String javaTowerGet(){
        return "javaTower";
    }

    @GetMapping("/profile")
    public String profileGet(Principal principal, Model model) {
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);

        if (gamer != null) {
            model.addAttribute("gamer", gamer);
            return "redirect:/CodeUp/profile/" + gamer.getIdUser();
        } else {
            return "menu";
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

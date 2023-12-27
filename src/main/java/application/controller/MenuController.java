package application.controller;

import application.entity.Gamer;
import application.service.GamerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Arrays;
import java.util.Base64;

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
        model.addAttribute("month", new String[]{"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"});
        if (gamer.getImage() != null) {
            model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
        }
        return "settings";
    }
    @PostMapping("/settings/{id}")
    public String settingsPost(@PathVariable String id,
                               @ModelAttribute @Valid Gamer gamer,
                               @RequestParam(value = "photoInput") MultipartFile image,
                               @RequestParam(value = "day") String day,
                               @RequestParam(value = "month") String month,
                               @RequestParam(value = "year") String year){
        if (!gamerService.updateGamer(gamer, Long.parseLong(id), image, day + "." + month + "." + year)) {
            return "redirect:/CodeUp/settings?error=true";
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
            if (gamer.getImage() != null && gamer.getImage().length != 0) {
                model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
            }
            return "profile";
        } else {
            return "redirect:/CodeUp/menu";
        }
    }
}

package application.controller;

import application.entity.Gamer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import application.service.GamerService;


@Controller
@RequestMapping("/")
public class InputController {
    @Autowired
    private GamerService gamerService;

    @GetMapping("/")
    public String start() {
        return "index";
    }

    @GetMapping("/registration")
    public String registrationGet(Model model) {
        model.addAttribute("gamer", new Gamer());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@ModelAttribute("gamer") @Validated Gamer gamer, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!gamerService.saveGamer(gamer)){
            model.addAttribute("errorMessage", "Пользователь с таким именем уже существует");
            return "registration";
        }

        return "redirect:/customLogin";
    }

    @GetMapping("/login")
    public String loginGet(Model model) {
        model.addAttribute("gamer", new Gamer());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute("gamer") @Validated Gamer gamer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "login";
        }
        if (!gamerService.loginGamer(gamer.getEmail(), gamer.getPassword())){
            return "login";
        }

        return "redirect:/CodeUp/menu";
    }
}

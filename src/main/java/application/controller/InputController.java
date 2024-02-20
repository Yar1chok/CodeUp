package application.controller;

import application.entity.Gamer;
import application.service.CipherService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import application.service.GamerService;

import java.util.Base64;


@Controller
@RequestMapping("/")
public class InputController {
    private final GamerService gamerService;
    private final CipherService cipherService;

    public InputController(CipherService cipherService, GamerService gamerService) {
        this.cipherService = cipherService;
        this.gamerService = gamerService;
    }


    @GetMapping("/")
    public String start() {
        return "index";
    }

    @GetMapping("/registration")
    public String registrationGet(Model model) {
        model.addAttribute("publicKey", cipherService.getPublicKey());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@RequestParam("encodedEmail") String encryptedEmail,
                                   @RequestParam("encodedNickname") String encryptedNickname,
                                   @RequestParam("encodedPassword") String encryptedPassword,
                                   Model model) {
        String email = cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedEmail)));
        String nickname = cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedNickname)));
        String password = cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedPassword)));
        Gamer gamer = new Gamer();
        gamer.setEmail(email);
        gamer.setNickname(nickname);
        gamer.setPassword(password);
        if (!gamerService.saveGamer(gamer)){
            model.addAttribute("errorMessage", "Пользователь с таким именем уже существует");
            model.addAttribute("publicKey", cipherService.getPublicKey());
            return "registration";
        }

        return "redirect:/customLogin";
    }

    @GetMapping("/login")
    public String loginGet(Model model) {
        model.addAttribute("gamer", new Gamer());
        model.addAttribute("publicKey", cipherService.getPublicKey());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam("encodedEmail") String encryptedEmail,
                            @RequestParam("encodedPassword") String encryptedPassword,
                            Model model) {
        String email = cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedEmail)));
        String password = cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedPassword)));
        if (!gamerService.loginGamer(email, password)){
            model.addAttribute("publicKey", cipherService.getPublicKey());
            return "login";
        }
        return "redirect:/CodeUp/menu";
    }
}

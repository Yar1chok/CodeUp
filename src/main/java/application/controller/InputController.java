package application.controller;

import application.entity.Gamer;
import application.service.CipherService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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
        model.addAttribute("gamer", new Gamer());
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationPost(@RequestParam("encodedEmail") String encryptedEmail,
                                   @RequestParam("encodedNickname") String encryptedNickname,
                                   @RequestParam("encodedPassword") String encryptedPassword,
                                   Model model) {
        // Не удалять
        //String email = cipherService.decrypt(encryptedEmail);
        //System.out.println(email);
        //String nickname = cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedNickname)));
        //System.out.println(nickname);
        //String password = cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedPassword)));
        //System.out.println(password);
        Gamer gamer = new Gamer();
        gamer.setEmail(encryptedEmail);
        gamer.setNickname(encryptedNickname);
        gamer.setPassword(encryptedPassword);
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

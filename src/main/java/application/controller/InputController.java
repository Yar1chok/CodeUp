package application.controller;

import application.entity.Gamer;
import application.service.CipherService;
import application.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import application.service.GamerService;

import java.util.Base64;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/")
public class InputController {
    private final GamerService gamerService;
    private final CipherService cipherService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    public InputController(CipherService cipherService,
                           GamerService gamerService,
                           EmailService emailService,
                           PasswordEncoder passwordEncoder) {
        this.cipherService = cipherService;
        this.gamerService = gamerService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
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
        gamer.setVerificationToken(email + UUID.randomUUID());
        if (!gamerService.saveGamer(gamer)){
            model.addAttribute("errorMessage", "Пользователь с таким именем уже существует");
            model.addAttribute("publicKey", cipherService.getPublicKey());
            return "registration";
        }
        emailService.sendVerificationEmail(gamer.getEmail(), gamer.getVerificationToken());
        return "redirect:/confirmation?email="+gamer.getEmail();
    }

    @GetMapping("/confirmation")
    public String confirmationGet(Model model, @RequestParam(value = "email") String email) {
        model.addAttribute("email", email);
        model.addAttribute("description", "Перейдите по ссылке, которая\n" +
                "            пришла вам в сообщении на почте, чтобы активировать ваш аккаунт.\n" +
                "            Подтвержденные аккаунты дают доступ к еще большим возможностям\n" +
                "            CodeUp!");
        model.addAttribute("title", "Подтверждение электронной почты");
        return "confirmation";
    }

    @GetMapping("/login")
    public String loginGet(Model model) {
        model.addAttribute("gamer", new Gamer());
        model.addAttribute("publicKey", cipherService.getPublicKey());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam("encodedEmail") String encryptedEmail,
                            @RequestParam("encodedPassword") String encryptedPassword) {
        String email = cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedEmail)));
        String password = cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedPassword)));
        if (!gamerService.loginGamer(email, password) || !gamerService.checkVerification(email)){
            return "redirect:/login";
        }
        return "redirect:/CodeUp/menu";
    }

    @GetMapping("/verify-email")
    public String verifyEmailGet(@RequestParam(value = "token") String token) {
        Gamer gamer = this.gamerService.findGamerByVerificationToken(token);
        if (gamer != null){
            if(this.gamerService.activateEmail(gamer.getEmail())){
                return "redirect:/login?verify=true";
            }
        }
        return "redirect:/login?error_verify=true";
    }

    @GetMapping("/forget-password")
    public String forgetPasswordGet(Model model) {
        model.addAttribute("publicKey", cipherService.getPublicKey());
        return "forgetPassword";
    }

    @PostMapping("/forget-password")
    public String forgetPasswordPost(@RequestParam(value = "encodedEmail") String encryptedEmail, Model model) {
        String email = cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedEmail)));
        Gamer gamer = this.gamerService.findGamerByEmail(email);
        if (gamer != null && gamer.isEmailVerified()){
            this.emailService.sendResetPassword(email);
            model.addAttribute("email", email);
            model.addAttribute("description", "Перейдите по ссылке, которая\n" +
                    "            пришла вам в сообщении на почте, чтобы сменить пароль.");
            model.addAttribute("title", "Сброс пароля");
            return "confirmation";
        }
        return "redirect:/login?change_password_error=true";
    }


    @PostMapping("/reset-password")
    public String resetPasswordPost(@RequestParam(value = "email") String email,
                                     @RequestParam(value = "encodedPassword") String encryptedPassword) {
        List<Gamer> gamerList = this.gamerService.findAll();
        if (gamerList != null){
            for(Gamer gamer : gamerList){
                if (this.passwordEncoder.matches(gamer.getEmail(), email)){
                    gamer.setPassword(passwordEncoder.encode(cipherService.decrypt(new String(Base64.getDecoder().decode(encryptedPassword)))));
                    this.gamerService.justUpdate(gamer);
                    return "redirect:/login?change_password=true";
                }
            }
        }
        return "redirect:/login?change_password_error=true";
    }

    @GetMapping("/reset-password")
    public String resetPasswordGet(Model model) {
        model.addAttribute("publicKey", cipherService.getPublicKey());
        return "createNewPassword";
    }
}

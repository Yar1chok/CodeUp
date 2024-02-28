package application.controller;

import application.entity.Gamer;
import application.entity.JavaTower;
import application.repository.JavaTowerRepository;
import application.service.GamerService;
import application.service.JavaTowerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/CodeUp")
public class MenuController {

    private final GamerService gamerService;
    private final JavaTowerService javaTowerService;

    @Autowired
    public MenuController(GamerService gamerService, JavaTowerService javaTowerService) {
        this.gamerService = gamerService;
        this.javaTowerService = javaTowerService;
    }

    @GetMapping("/menu")
    public String menuGet(Model model, Principal principal){
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);
        if (gamer != null) {
            if (gamer.getImage() != null) {
                model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
            }
            return "menu";
        } else {
            return "redirect:/customLogin";
        }
    }

    @GetMapping("/settings")
    public String settingsGet(Principal principal){
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);
        if (gamer != null) {
            return "redirect:/CodeUp/settings/" + gamer.getIdUser();
        } else {
            return "redirect:/login";
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
    public String levelGet(Model model, Principal principal){
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);
        if (gamer != null) {
            if (gamer.getImage() != null) {
                model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
            }
            return "level";
        } else {
            return "redirect:/customLogin";
        }
    }

    @GetMapping("/javaTower")
    public String javaTowerGet(Model model, Principal principal){
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);
        if (gamer != null) {
            if (gamer.getImage() != null) {
                model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
            }
            return "javaTower";
        } else {
            return "redirect:/customLogin";
        }
    }

    @GetMapping("/profile")
    public String profileGet(Principal principal, Model model) {
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);

        if (gamer != null) {
            if (gamer.getImage() != null) {
                model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
            }
            model.addAttribute("gamer", gamer);
            return "redirect:/CodeUp/profile/" + gamer.getIdUser();
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/profile/{id}")
    public String profileById(@PathVariable Long id, Model model) {
        Gamer gamer = gamerService.findGamerById(id);

        if (gamer != null) {
            model.addAttribute("gamer", gamer);
            if (gamer.getImage() != null) {
                model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
            }
            return "profile";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/javaTower/level/{block}/{level}")
    public String javaLevelGet(Principal principal, Model model, @PathVariable Integer block,
                               @PathVariable Integer level) {
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);
        if (gamer != null) {
            if (gamer.getImage() != null) {
                model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
            }
            model.addAttribute("questions", javaTowerService.getShuffled(block, level));
            return "level";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/javaTower/level/{block}/{level}/result/")
    public String submitAnswers(@RequestParam Map<String, String> userAnswers,
                                Model model,
                                @PathVariable Integer block,
                                Principal principal, @PathVariable Integer level) {
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);
        if (gamer != null) {
            if (gamer.getImage() != null) {
                model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
            }
            List<JavaTower> questions = javaTowerService.findAllByBlockAndLevel(block, level);
            int correctAnswersCount = 0;

            for (JavaTower question : questions) {
                Long questionId = question.getIdQues();

                if (userAnswers.containsKey(questionId.toString())) {
                    String userAnswer = userAnswers.get(questionId.toString());
                    if (userAnswer.equals(question.getRightAnswer())) {
                        correctAnswersCount++;
                    }
                }
            }

            model.addAttribute("correctAnswersCount", correctAnswersCount);
            model.addAttribute("allQues", questions.size());
            if (((double) correctAnswersCount / questions.size()) >= 0.6) {
                if (gamer.getCurLvlJava() <= level){
                    level++;
                    if (javaTowerService.existByBlockAndLevel(block, level)) {
                        gamer.setCurLvlJava(level);
                        gamerService.justUpdate(gamer);
                    } else {
                        block++;
                        if (javaTowerService.existByBlock(block)){
                            gamer.setCurLvlJava(1);
                            gamer.setBlockJava(block);
                            gamerService.justUpdate(gamer);
                        }
                    }
                }
                model.addAttribute("success", true);
            } else {
                model.addAttribute("success", false);
            }
            return "result";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/javaMap")
    public String javaMap(Principal principal, Model model) {
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);
        if (gamer != null) {
            if (gamer.getImage() != null) {
                model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
            }
            if (gamer.getCurLvlJava() != null) {
                model.addAttribute("curLevel", gamer.getCurLvlJava());
            }
            return "javaThemesMap";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/compilate")
    public String compilate(Model model){
        return "compilate";
    }
}

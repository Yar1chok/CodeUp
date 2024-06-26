package application.controller;

import application.entity.Gamer;
import application.entity.JavaTower;
import application.service.GamerService;
import application.service.JavaTowerService;
import application.service.LevelsJavaService;
import com.google.gson.Gson;
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
    private final LevelsJavaService levelsJavaService;

    @Autowired
    public MenuController(GamerService gamerService,
                          JavaTowerService javaTowerService,
                          LevelsJavaService levelsJavaService) {
        this.gamerService = gamerService;
        this.javaTowerService = javaTowerService;
        this.levelsJavaService = levelsJavaService;
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
            return "redirect:/login";
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
            return "redirect:/CodeUp/settings/" + id + "?error=true";
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
            return "redirect:/login";
        }
    }

    @GetMapping("/javaTower/{block}")
    public String javaTowerGet(Model model, Principal principal, @PathVariable Integer block){
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);
        if (gamer != null) {
            if (gamer.getBlockJava() < block){
                return "redirect:/javaMap";
            }
            if (gamer.getImage() != null) {
                model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
            }
            model.addAttribute("username", gamer.getNickname());
            model.addAttribute("curBlock", gamer.getBlockJava());
            model.addAttribute("curLevel", gamer.getCurLvlJava());
            model.addAttribute("levels", levelsJavaService.findAllByBlock(block));
            return "javaTower";
        } else {
            return "redirect:/login";
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
            model.addAttribute("username", gamer.getNickname());
            //model.addAttribute("block", block);
            return "level";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/javaTower/level/{block}/{level}/result/")
    public String submitAnswers(@RequestParam(value = "userAnswers") String userAnswers,
                                Model model,
                                @PathVariable Integer block,
                                Principal principal, @PathVariable Integer level) {
        Gson gson = new Gson();
        Map<String, String> userAnswersMap = gson.fromJson(userAnswers, Map.class);
        String email = principal.getName();
        Gamer gamer = gamerService.findGamerByEmail(email);
        if (gamer != null) {
            if (gamer.getImage() != null) {
                model.addAttribute("image", Base64.getEncoder().encodeToString(gamer.getImage()));
            }
            List<JavaTower> questions = javaTowerService.findAllByBlockAndLevel(block, level);
            int correctAnswersCount = 0;

            for (JavaTower question : questions) {
                String questionText= question.getTextQues();
                if (userAnswersMap.containsKey(questionText)) {
                    String userAnswer = userAnswersMap.get(questionText);
                    if (userAnswer.equals(question.getRightAnswer())) {
                        correctAnswersCount++;
                    }
                }
            }
            model.addAttribute("correctAnswersCount", correctAnswersCount);
            model.addAttribute("allQues", questions.size());
            model.addAttribute("block", block);
            model.addAttribute("level", level);
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
            if (gamer.getBlockJava() != null) {
                model.addAttribute("curBlock", gamer.getBlockJava());
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

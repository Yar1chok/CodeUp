package application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/CodeUp")
public class MenuController {

    @GetMapping("/menu")
    public String menuGet(){
        return "menu";
    }
}

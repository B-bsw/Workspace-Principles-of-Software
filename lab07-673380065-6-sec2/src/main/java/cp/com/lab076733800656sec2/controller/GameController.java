package cp.com.lab076733800656sec2.controller;

import cp.com.lab076733800656sec2.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("games", gameService.getAll());
        return "list";
    }
}

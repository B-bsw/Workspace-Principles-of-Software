package cp.com.lab076733800656sec2.controller;

import cp.com.lab076733800656sec2.model.Game;
import cp.com.lab076733800656sec2.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("games", gameService.getAll());
        return "games/list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("game", new Game());
        return "games/add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Game game) {
        gameService.saveGame(game);
        return "redirect:/games";
    }
}

package cp.com.lab076733800656sec2.controller;

import cp.com.lab076733800656sec2.model.Game;
import cp.com.lab076733800656sec2.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
        model.addAttribute("message", "Add new game Success!");
        return "games/list";
    } // Page List

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("game", new Game());
        return "games/add";
    } // Page Add

    @PostMapping("/save")
    public String save(@ModelAttribute Game game) {
        gameService.saveGame(game);
        return "redirect:/games";
    } // Add

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable int id) {
        model.addAttribute("game", gameService.getById(id));
        return "games/delete";
    } // Page Delete

    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable int id) {
        gameService.deleteByid(id);
        return "redirect:/games";
    } // Delete By Id

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        model.addAttribute("game", gameService.getById(id));
        System.out.println(
            "Release Date: " + gameService.getById(id).getReleaseDate()
        );
        return "games/edit";
    } // Page Edit

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, @ModelAttribute Game game) {
        game.setId(id);
        gameService.saveGame(game);
        return "redirect:/games";
    } // Edit By Id
}

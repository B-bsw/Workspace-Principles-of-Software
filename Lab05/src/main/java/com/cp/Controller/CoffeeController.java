package com.cp.Controller;

import com.cp.Model.Coffee;
import com.cp.Service.CoffeeService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coffees")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping()
    public List<Coffee> getAllCoffees() {
        return coffeeService.getAllCoffees();
    }

    @PostMapping()
    public Coffee addCoffee(@RequestBody Coffee coffee) {
        return coffeeService.addCoffee(coffee);
    }

    @GetMapping("/{id}")
    public Coffee getCoffee(@PathVariable int id) {
        return coffeeService.getCoffeeById(id);
    }

    @PutMapping("/{id}")
    public Coffee updateCoffee(
        @PathVariable int id,
        @Valid @RequestBody Coffee coffee
    ) {
        return coffeeService.updateCoffee(id, coffee);
    }

    @DeleteMapping("/{id}")
    public String deleteCoffee(@PathVariable int id) {
        return coffeeService.deleteCoffee(id);
    }
}

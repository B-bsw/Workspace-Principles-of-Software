package com.cp.Service;

import com.cp.Model.Coffee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CoffeeService {
    private List<Coffee> coffees = new ArrayList<>();
    private int index = 1;

    public List<Coffee> getAllCoffees() {
        return coffees;
    }

    public Coffee getCoffeeById(int id) {
        for (Coffee coffee : coffees) {
            if (coffee.getId() == id) {
                return coffee;
            }
        }
        return null;
    }

    public Coffee addCoffee(Coffee coffee) {
        coffee.setId(index++);
        coffees.add(coffee);
        return coffee;
    }

    public Coffee updateCoffee(int id, Coffee coffee) {
        for (Coffee c : coffees) {
            if (c.getId() == id) {
                c.setName(coffee.getName());
                c.setPrice(coffee.getPrice());
                return c;
            }
        }
        return null;
    }

    public String deleteCoffee(int id) {
        if (coffees.removeIf(coffee -> coffee.getId() == id)) {
            return "Success to delete";
        }
        return "Success to delete";
    }

}

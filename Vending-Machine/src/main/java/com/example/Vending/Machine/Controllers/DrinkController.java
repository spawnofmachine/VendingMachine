package com.example.Vending.Machine.Controllers;

import com.example.Vending.Machine.DAOs.DrinkDao;
import com.example.Vending.Machine.Models.Drink;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Drink")
public class DrinkController {
    private DrinkDao drinkDao;

    public DrinkController(DrinkDao drinkDao) {
        this.drinkDao = drinkDao;
    }

    @GetMapping("")
    public List<Drink> getDrinks(@RequestParam(name = "name", defaultValue = "") String name) {
        if (!name.isEmpty()) {
            return List.of(drinkDao.getDrinkByName(name));
        }
        return drinkDao.getDrinks();
    }

    @GetMapping("/{drinkId}")
    public Drink getDrinkById(@PathVariable int drinkId) {
        return drinkDao.getDrinkById(drinkId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Drink addDrink(@Valid @RequestBody Drink drink) {
        return drinkDao.addDrink(drink);
    }

    @PutMapping("/{drinkId}")
    public Drink updateDrink(@PathVariable int drinkId, @Valid @RequestBody Drink drink) {
        drink.setDrinkId(drinkId);
        return drinkDao.updateDrink(drink);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{drinkId}")
    public int deleteDrink(@PathVariable int drinkId) {
        return drinkDao.deleteDrink(drinkId);
    }
}

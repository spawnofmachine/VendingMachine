package com.example.Vending.Machine.Controllers;

import com.example.Vending.Machine.DAOs.DrinkInventoryDao;
import com.example.Vending.Machine.Models.DrinkInventory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/DrinkInventory")
public class DrinkInventoryController {
    private DrinkInventoryDao drinkInventoryDao;

    public DrinkInventoryController(DrinkInventoryDao drinkInventoryDao) {
        this.drinkInventoryDao = drinkInventoryDao;
    }

    @GetMapping("")
    public List<DrinkInventory> getAllDrinkQuantities(@RequestParam(name = "drinkQuantity", defaultValue = "0") int drinkQuantity) {
        if (drinkQuantity > 0) {
            return drinkInventoryDao.getAllDrinkQuantitiesBelowpercent(drinkQuantity);
        }
        return drinkInventoryDao.getAllDrinkQuantities();
    }

    @GetMapping("/{drinkInventoryId}")
    public DrinkInventory getDrinkQuantityById(@PathVariable int drinkInventoryId) {
        return drinkInventoryDao.getDrinkQuantityById(drinkInventoryId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public DrinkInventory addDrinkQuantity(@Valid @RequestBody DrinkInventory drinkInventory) {
        return drinkInventoryDao.addDrinkQuantity(drinkInventory);
    }

    @PutMapping("/{drinkInventoryId}")
    public DrinkInventory updateDrinkQuantity(@PathVariable int drinkInventoryId,
                                              @Valid @RequestBody DrinkInventory drinkInventory) {
        drinkInventory.setDrinkInventoryId(drinkInventoryId);
        return drinkInventoryDao.updateDrinkQuantity(drinkInventory);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{drinkInventoryId}")
    public int deleteDrinkQuantity(@PathVariable int drinkInventoryId) {
        return drinkInventoryDao.deleteDrinkQuantity(drinkInventoryId);
    }
}

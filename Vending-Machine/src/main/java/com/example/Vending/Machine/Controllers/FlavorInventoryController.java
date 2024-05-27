package com.example.Vending.Machine.Controllers;

import com.example.Vending.Machine.DAOs.FlavorInventoryDao;
import com.example.Vending.Machine.Models.FlavorInventory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/FlavorInventory")
public class FlavorInventoryController {
    private FlavorInventoryDao flavorInventoryDao;

    public FlavorInventoryController(FlavorInventoryDao flavorInventoryDao) {
        this.flavorInventoryDao = flavorInventoryDao;
    }

    @GetMapping("")
    public List<FlavorInventory> getAllFlavorQuantities(@RequestParam(name = "flavorQuantity", defaultValue = "0") int flavorQuantity,
                                                        @RequestParam(name = "name", defaultValue = "") String name) {
        if (flavorQuantity > 0 && name.isEmpty()) {
            return flavorInventoryDao.getAllFlavorsBelowPercent(flavorQuantity);
        } else if (flavorQuantity == 0 && !name.isEmpty()) {
            return List.of(flavorInventoryDao.getFlavorQuantityByName(name));
        }
        return flavorInventoryDao.getAllFlavorQuantities();
    }

    @GetMapping("/{flavorInventoryId}")
    public FlavorInventory getFlavorInventoryById(@PathVariable int flavorInventoryId) {
        return flavorInventoryDao.getFlavorInventoryById(flavorInventoryId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public FlavorInventory addFlavorQuantity(@Valid @RequestBody FlavorInventory flavorInventory) {
        return flavorInventoryDao.addFlavorQuantity(flavorInventory);
    }

    @PutMapping("/{flavorInventoryId}")
    public FlavorInventory updateFlavor(@PathVariable int flavorInventoryId,
                                        @Valid @RequestBody FlavorInventory flavorInventory) {
        flavorInventory.setFlavorInventoryId(flavorInventoryId);
        return flavorInventoryDao.updateFlavor(flavorInventory);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{flavorInventoryId}")
    public int deleteFlavorQuantity(@PathVariable int flavorInventoryId) {
        return flavorInventoryDao.deleteFlavorQuantity(flavorInventoryId);
    }
}

package com.example.Vending.Machine.Controllers;

import com.example.Vending.Machine.DAOs.CupInventoryDao;
import com.example.Vending.Machine.Models.CupInventory;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/CupInventory")
public class CupInventoryController {
    private CupInventoryDao cupInventoryDao;

    public CupInventoryController(CupInventoryDao cupInventoryDao) {
        this.cupInventoryDao = cupInventoryDao;
    }

    @GetMapping("")
    public List<CupInventory> getAllCupsQuantity(@RequestParam(name = "cupQuantity", defaultValue = "0") int cupQuantity,
                                                 @RequestParam(name = "size", defaultValue = "") String size) {
        if (cupQuantity > 0 && size.isEmpty()) {
            return cupInventoryDao.getAllCupsBelowQuantity(cupQuantity);
        } else if (cupQuantity == 0 && !size.isEmpty()) {
            return List.of(cupInventoryDao.getCupQuantityBySize(size));
        }
        return cupInventoryDao.getAllCupsQuantity();
    }

    @GetMapping("/{cupInventoryId}")
    public CupInventory getCupInventoryById(@PathVariable int cupInventoryId) {
        return cupInventoryDao.getCupInventoryById(cupInventoryId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public CupInventory addCupQuantity(@Valid @RequestBody CupInventory cupInventory) {
        return cupInventoryDao.addCupQuantity(cupInventory);
    }

    @PutMapping("/{cupInventoryId}")
    public CupInventory updateCupQuantity(@PathVariable int cupInventoryId,
                                          @Valid @RequestBody CupInventory cupInventory) {
        cupInventory.setCupInventoryId(cupInventoryId);
        return cupInventoryDao.updateCupQuantity(cupInventory);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{cupInventoryId}")
    public int deleteCupQuantity(@PathVariable int cupInventoryId) {
        return cupInventoryDao.deleteCupQuantity(cupInventoryId);
    }
}

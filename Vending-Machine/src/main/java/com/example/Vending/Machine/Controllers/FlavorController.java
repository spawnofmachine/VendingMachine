package com.example.Vending.Machine.Controllers;

import com.example.Vending.Machine.DAOs.FlavorDao;
import com.example.Vending.Machine.Models.Flavor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Flavor")
public class FlavorController {
    private FlavorDao flavorDao;

    public FlavorController(FlavorDao flavorDao) {
        this.flavorDao = flavorDao;
    }

    @GetMapping("")
    public List<Flavor> getFlavors(@RequestParam(name = "name", defaultValue = "") String name) {
        if (!name.isEmpty()) {
            return List.of(flavorDao.getFlavorByName(name));
        }
        return flavorDao.getFlavors();
    }

    @GetMapping("/{flavorId}")
    public Flavor getFlavorById(@PathVariable int flavorId) {
        return flavorDao.getFlavorById(flavorId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Flavor addFlavor(@Valid @RequestBody Flavor flavor) {
        return flavorDao.addFlavor(flavor);
    }

    @PutMapping("/{flavorId}")
    public Flavor updateFlavor(@PathVariable int flavorId, @Valid @RequestBody Flavor flavor) {
        flavor.setFlavorId(flavorId);
        return flavorDao.updateFlavor(flavor);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{flavorId}")
    public int deleteFlavor(@PathVariable int flavorId) {
        return flavorDao.deleteFlavor(flavorId);
    }
}

package com.example.Vending.Machine.Controllers;

import com.example.Vending.Machine.DAOs.CupDao;
import com.example.Vending.Machine.Models.Cup;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Cup")
public class CupController {
    private CupDao cupDao;

    public CupController(CupDao cupDao) {
        this.cupDao = cupDao;
    }

    @GetMapping("")
    public List<Cup> getCups(@RequestParam(name = "size", defaultValue = "") String size) {
        if (!size.isEmpty()) {
            return List.of(cupDao.getCupBySize(size));
        }
        return cupDao.getCups();
    }

    @GetMapping("/{cupId}")
    public Cup getCupById(@PathVariable int cupId) {
        return cupDao.getCupById(cupId);
    }

//    @GetMapping("/{size}")
//    public Cup getCupBySize(@PathVariable String size) {
//        return cupDao.getCupBySize(size);
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Cup addCup(@Valid @RequestBody Cup cup) {
        return cupDao.addCup(cup);
    }

    @PutMapping("/{cupId}")
    public Cup updateCup(@PathVariable int cupId, @Valid @RequestBody Cup cup) {
        cup.setCupId(cupId);
        return cupDao.updateCup(cup);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{cupId}")
    public int deleteCup(@PathVariable int cupId) {
        return cupDao.deleteCup(cupId);
    }
}

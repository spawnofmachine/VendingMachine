package com.example.Vending.Machine.Models;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkInventory {

    private int drinkInventoryId;

    @Min(value = 1, message = "DrinkId must be greater than 0")
    private int drinkId;

    private int drinkQuantity;

}

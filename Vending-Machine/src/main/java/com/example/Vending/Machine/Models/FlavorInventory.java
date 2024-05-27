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
public class FlavorInventory {

    private int flavorInventoryId;

    @Min(value = 1, message = "FlavorId must be greater than 0")
    private int flavorId;

    private int flavorQuantity;

}

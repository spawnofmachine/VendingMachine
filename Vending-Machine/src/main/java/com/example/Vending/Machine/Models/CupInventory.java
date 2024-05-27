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
public class CupInventory {

    private int cupInventoryId;

    @Min(value = 1, message = "CupId must be greater than 1")
    private int cupId;

    private int cupQuantity;

}

package com.example.Vending.Machine.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Drink {

    private int drinkId;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters")
    private String name;

    @NotBlank(message = "type is required")
    private String type;

    private double cost;

    private boolean canBeFlavored;

}

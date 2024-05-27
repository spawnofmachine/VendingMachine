package com.example.Vending.Machine.Models;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flavor {

    private int flavorId;

    @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters")
    private String name;
    private final double COST = .20;

}

package com.example.Vending.Machine.Models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cup {

    private int cupId;

    @NotBlank(message = "Size is required")
    private String size;

    private int capacity;

    private double cost;

}

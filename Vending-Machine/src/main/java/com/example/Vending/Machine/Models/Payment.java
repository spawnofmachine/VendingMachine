package com.example.Vending.Machine.Models;

import jakarta.validation.constraints.Min;
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
public class Payment {

    private int payment_id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 25, message = "Name must be between 2 and 25 characters")
    private String name; //Should this be name as in Branch or bank or saved favorite name?

    @NotBlank(message = "Type is required")
    @Size(min = 2, max = 25, message = "Type must be between 2 and 25 characters")
    private String type;

}

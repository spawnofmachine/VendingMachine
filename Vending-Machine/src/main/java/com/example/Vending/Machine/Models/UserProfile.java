package com.example.Vending.Machine.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String favoriteDrink;
    private String favoriteFlavors;
    private String savedPaymentName;
}

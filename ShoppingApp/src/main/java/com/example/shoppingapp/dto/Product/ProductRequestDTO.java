package com.example.shoppingapp.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "Product name must not be blank")
    private String name;

    @NotBlank(message = "Product description must not be blank")
    private String description;

    @Positive(message = "Retail price must be greater than 0")
    private double retailPrice;

    @Positive(message = "Wholesale price must be greater than 0")
    private double wholesalePrice;

    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
}


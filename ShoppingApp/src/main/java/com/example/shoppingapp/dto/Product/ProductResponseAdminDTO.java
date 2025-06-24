package com.example.shoppingapp.dto.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseAdminDTO extends ProductResponseDTO {
    private int quantity;
    private Double wholesalePrice;

    public ProductResponseAdminDTO(Long productId, String name, String description, Double retailPrice, int quantity, Double wholesalePrice) {
        super(productId, name, description, retailPrice);
        this.quantity = quantity;
        this.wholesalePrice = wholesalePrice;
    }
}

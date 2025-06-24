package com.example.shoppingapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name="Quantity", nullable = false)
    private int quantity;

    @Column(name = "retail_price", nullable = false)
    private double retailPrice;

    @Column(name = "wholesale_price", nullable = false)
    private double wholesalePrice;

}

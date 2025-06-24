package com.example.shoppingapp.exception;

public class ProductAlreadyExistsInWatchlist extends RuntimeException {
    public ProductAlreadyExistsInWatchlist(String message) {
        super(message);
    }
}

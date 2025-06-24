package com.example.shoppingapp.exception;

public class OrderStatusInvalidException extends RuntimeException{
    public OrderStatusInvalidException(String message) {
        super(message);
    }
}

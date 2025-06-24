package com.example.shoppingapp.exception;

public class WatchlistEntryNotFoundException extends RuntimeException{

    public WatchlistEntryNotFoundException(String message) {
        super(message);
    }
}

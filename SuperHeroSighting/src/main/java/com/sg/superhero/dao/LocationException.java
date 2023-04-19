package com.sg.superhero.dao;

public class LocationException extends Exception {
    public LocationException(String msg) {
        super(msg);
    }
    
    public LocationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}

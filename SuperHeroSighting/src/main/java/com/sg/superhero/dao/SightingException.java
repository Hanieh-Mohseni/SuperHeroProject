package com.sg.superhero.dao;

public class SightingException extends Exception {
    public SightingException(String msg) {
        super(msg);
    }
    
    public SightingException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}

package com.example.demo;

public class UserNotFoundException extends RuntimeException{
    UserNotFoundException(long id) {
        super("Couldn't find user "+ id);
    }
}

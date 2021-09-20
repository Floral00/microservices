package fr.polytech.service2;

public class UserNotFoundException extends RuntimeException{
    UserNotFoundException(long id) {
        super("Couldn't find user "+ id);
    }
}

package fr.polytech.service2;

public class Token {

    private long userId;
    private String token;

    public Token(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setuserId(long userId) {
        this.userId = userId;
    }


}

package fr.polytech.service2;

import java.util.Date;
import java.util.Random;

public class Token {

    private long userId;
    private long id;
    private String token;
    private Date date_creation;
    public Token(long id, long userId) {
        this.userId = userId;
        this.id = id;
        this.date_creation = new Date();
        this.token = generateToken();
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getToken() {
        return token;
    }
    private String generateToken() {
        String token="";
        Random rand = new Random();
        for(int i = 0 ; i < 5 ; i++){
            char c = (char)(rand.nextInt(26) + 97);
            token += c;
        }
        return token;
    }
}

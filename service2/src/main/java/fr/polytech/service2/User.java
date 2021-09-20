package fr.polytech.service2;

public class User {
    private long id;
    private String name;
    private String password;
    private String token;

    public long getId() {
        return id;
    }

    public User(long id, String name, String password, String token) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.token = token;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

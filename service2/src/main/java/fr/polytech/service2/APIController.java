package fr.polytech.service2;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;


@RestController
public class APIController {
    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();
    private final AtomicLong counterToken = new AtomicLong();
    private final Map<Long, Token> tokens = new HashMap<>();


    @GetMapping("/users")
    public Collection<User> users() {
        return users.values();
    }

    @PostMapping("/users")
    public User create_user(@RequestBody @Valid User user) {
        long new_id_user = counter.incrementAndGet();
        long new_id_token = counterToken.incrementAndGet();
        Token token_user = new Token(new_id_token, new_id_user);
        tokens.put(new_id_token, token_user);
        user.setId(new_id_user);
        users.put(new_id_user, user);
        return user;
    }
    @PostMapping("/users/{id}/token")
    public Token generate_token(@PathVariable(value = "id") Long id) {
        long new_id_token = counterToken.incrementAndGet();
        Token token_user = new Token(new_id_token, id);
        tokens.put(id, token_user);
        return token_user;
    }
    @GetMapping("/users/{id}")
    public User specific_user(@PathVariable(value = "id") Long id) {
        if(!users.containsKey(id)) {
            throw new UserNotFoundException(id);
        }
        return users.get(id);

    }

    @GetMapping("/users/{id}/name")
    public String specific_user_name(@PathVariable(value = "id") Long id) {
        if(!users.containsKey(id)) {
            throw new UserNotFoundException(id);
        }
        return users.get(id).getName();

    }

    @DeleteMapping("/users/{id}")
    public String delete_user(@PathVariable(value = "id") Long id, @RequestHeader(value="X-Token") String token) {
        String result = "Aucun élement supprimé";
        if (users.containsKey(id)) {
            if(tokens.get(id).getToken().equals(token)) {
                users.remove(id);
                tokens.remove(id);
                result = String.format("L'utilisateur %s a été supprimé!", id.toString());
            }
            else {
                result = "Token invalide";
            }
        }
        return result;
    }

    @DeleteMapping("/users/{id}/token")
    public String delete_token(@PathVariable(value = "id") Long id, @RequestHeader(value="X-Token") String token) {
        String result = "Aucun token supprimé";
        if(users.containsKey(id)) {
            if(tokens.containsKey(id)) {
                if(tokens.get(id).getToken().equals(token)) {
                    tokens.remove(id);
                    result = String.format("tokens " + token + " pour l'utilisateur "+ id.toString() + " supprimé");
                }
            }
        }
        else {
            throw new UserNotFoundException(id);
        }
        return result;
    }

    @GetMapping("/token")
    public String verif_token(@RequestHeader(value="X_Token") String token){
        String result = "";
        for(Map.Entry<Long, Token> tokens_index: tokens.entrySet()) {
            Token token_now = tokens_index.getValue();
            Long key = tokens_index.getKey();
            Date now = new Date();
            if(token_now.getToken().equals(token)) {
                if (Math.abs(now.getTime() - token_now.getDate_creation().getTime()) < 5) {
                    //Valid
                    result = token;
                } else {
                    //Invalid
                    tokens.remove(key);
                    Long token_id = counterToken.incrementAndGet();
                    Token new_token = new Token(token_id, token_now.getUserId());
                    tokens.put(key, new_token);
                    result = new_token.getToken();
                }
            }
        }
        return result;
    }

    @PostMapping("/users/{id}/name")
    public String edit_user_name(@PathVariable(value = "id") Long id, @RequestBody @Valid String name, @RequestHeader(value="X-Token") String token) {
        String result = "";
        if(!users.containsKey(id)) {
            throw new UserNotFoundException(id);
        }
        else {
            result = "Le token ne correspond pas";
            if(tokens.get(id).getToken().equals(token)) {
                users.get(id).setName(name);
                result = users.get(id).toString();
            }
        }
        return result;
    }

    @PostMapping("/users/{id}/password")
    public String edit_user_password(@PathVariable(value = "id") Long id, @RequestBody @Valid String password, String token) {
        String result = "";
        if(!users.containsKey(id)) {
            throw new UserNotFoundException(id);
        }
        else {
            result = "Le token ne correspond pas";
            if(tokens.get(id).getToken().equals(token)) {
                users.get(id).setPassword(password);
                result = users.get(id).getPassword();
            }
        }
        return result;
    }

}

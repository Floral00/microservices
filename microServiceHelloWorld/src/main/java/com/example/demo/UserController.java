package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {

    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/users")
    public Collection<User> users() {
        return users.values();
    }

    @PostMapping("/users")
    public User create_user(@RequestBody User user) {
        long new_id = counter.incrementAndGet();
        user.setId(new_id);
        users.put(new_id, user);

        return user;
    }

    @GetMapping("/users/{id}")
    public User specific_user(@PathVariable(value = "id") Long id) {
        return users.get(id);
    }

    @DeleteMapping("/users/{id}")
    public String delete_user(@PathVariable(value = "id") Long id) {
        String result = "Aucun élement supprimé";
        if (users.containsKey(id)) {
            users.remove(id);
            result = String.format("L'utilisateur %s a été supprimé!", id.toString());
        }

        return result;
    }
    //@DeleteMapping
    //@putMapping
}

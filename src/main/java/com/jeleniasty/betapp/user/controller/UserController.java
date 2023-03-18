package com.jeleniasty.betapp.user.controller;

import com.jeleniasty.betapp.user.repository.entity.User;
import com.jeleniasty.betapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("user")
    public void saveUser(@RequestParam
                         String login,
                         String password,
                         String email) {
        userService.saveUser(login, password, email);
    }

    @DeleteMapping("user/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("user/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping("users")
    public Iterable<User> getUsers() {
        return userService.getUsers();
    }
}

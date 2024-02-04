package com.chitchat.controller;

import com.chitchat.dto.UserDto;
import com.chitchat.entity.User;
import com.chitchat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chitchat")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/user/signup")
    public ResponseEntity<UserDto> addUser(@RequestBody User user) {
        var userDto = this.service.saveUser(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/user/login")
    public ResponseEntity<UserDto> matchUser(@RequestBody User user) {
        var result = this.service.findByUserNameAndPassword(user);
        return result.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable("id") Long userId) {
        User user = this.service.getUserById(userId);
        if(user != null)
            return ResponseEntity.ok(user);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(this.service.getAllUsers());
    }

}

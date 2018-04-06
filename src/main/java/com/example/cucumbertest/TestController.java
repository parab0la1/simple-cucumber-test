package com.example.cucumbertest;

import com.example.cucumbertest.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
public class TestController {

    private User user;

    @PostConstruct
    private void init() {
        user = new User();
        user.setAge(12);
        user.setName("Ringo Rubelsson");
    }

    @GetMapping(value = "/version")
    public ResponseEntity<String> getVersion() {
        return new ResponseEntity<>("1.0", HttpStatus.OK);
    }

    @PostMapping(value = "/version")
    public ResponseEntity<String> createVersion(@RequestBody String version) {
        return new ResponseEntity<>(version, HttpStatus.CREATED);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<User> getUser() {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping(value = "/user")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        user.setName(user.getName());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user")
    public ResponseEntity<String> deleteUser() {
        return new ResponseEntity<>("User successfully removed", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/user/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

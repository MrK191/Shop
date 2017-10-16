package com.shop.controllers;


import com.shop.exceptions.UsernameExistsException;
import com.shop.model.User;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws UsernameExistsException {
        User newUser = userService.registerNewUserAccount(user);

        if (newUser == null) {
            return new ResponseEntity<User>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<User>(HttpStatus.CREATED);
    }

}

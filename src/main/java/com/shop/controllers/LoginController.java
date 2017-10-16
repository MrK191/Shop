package com.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping
    public ResponseEntity<?> loginPage() {
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

}

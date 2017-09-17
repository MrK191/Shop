package com.shop.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping(value = "/test")
    public ResponseEntity<?> login() {
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }


    @RequestMapping("/loginError")
    public String loginError(Model model) {

        model.addAttribute("loginError", true);
        return "login.html";
    }
}

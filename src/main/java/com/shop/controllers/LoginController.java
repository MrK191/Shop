package com.shop.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping(value = "/login")
    public String res(){
        return "login required";
    }

    @RequestMapping("/loginError")
    public String loginError(Model model) {

        model.addAttribute("loginError", true);
        return "login.html";
    }
}

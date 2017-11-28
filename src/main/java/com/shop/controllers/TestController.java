package com.shop.controllers;

import com.shop.model.User;
import com.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("test")
    public List<User> getuser(){
        Long lun = new Long(1);
        return userRepository.getAllUsers( );
    }
}

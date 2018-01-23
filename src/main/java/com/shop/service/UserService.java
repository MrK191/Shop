package com.shop.service;


import com.shop.exceptions.UsernameExistsException;
import com.shop.model.User;
import com.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User registerNewUserAccount(@RequestBody User user) throws UsernameExistsException {

        if (usernameExist(user.getUsername())) {
            throw new UsernameExistsException(
                    "There is an account with that username: "
                            + user.getUsername());
        }

        User newUser = User.builder().username(user.getUsername()).password(user.getPassword()).role("ROLE_USER").build();

        return userRepository.save(newUser);

    }

    public void modifyAndSaveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllBy();
    }

    public User getUserWithId(Long id) {
        return userRepository.getOne(id);
    }

    public User getCurrentLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(auth.getName());
    }

    private boolean usernameExist(String username) {
        User user = userRepository.findByUsername(username);

        return user != null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
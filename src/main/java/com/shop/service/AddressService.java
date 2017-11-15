package com.shop.service;

import com.shop.model.Address;
import com.shop.model.User;
import com.shop.repositories.AddressRepository;
import com.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public void saveUserAddress(Address address) {
        User currentUser = getCurrentUser();
        currentUser.setAddress(address);
        address.setUser(currentUser);

        addressRepository.save(address);
    }


    public void deleteCurrentAddress() {
        User currentUser = getCurrentUser();
        currentUser.setAddress(null);

        userRepository.save(currentUser);
    }

    private User getCurrentUser() {
        return userService.getCurrentLoggedInUser();
    }

}

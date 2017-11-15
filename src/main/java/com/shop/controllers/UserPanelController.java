package com.shop.controllers;

import com.shop.model.Address;
import com.shop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user-panel")
public class UserPanelController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/address")
    public void saveAddress(@RequestBody Address address) {

        addressService.saveUserAddress(address);
    }

    @DeleteMapping("/address")
    public void deleteAddress() {

        addressService.deleteCurrentAddress();
    }
}

package com.shop.controllers;

import com.shop.model.Address;
import com.shop.service.AddressService;
import com.shop.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user-panel")
public class UserPanelController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private Validator validator;

    @PostMapping("/address")
    public void saveAddress(@RequestBody Address address) {
        validator.validateAddress(address.getId());

        addressService.saveUserAddress(address);
    }

    @PutMapping("/address")
    public void updateAddress(@RequestBody Address address) {

        addressService.saveUserAddress(address);
    }

    @DeleteMapping("/address")
    public void deleteAddress() {

        addressService.deleteCurrentAddress();
    }
}

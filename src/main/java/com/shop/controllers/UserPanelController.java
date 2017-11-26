package com.shop.controllers;

import com.shop.model.Address;
import com.shop.service.AddressService;
import com.shop.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user-panel")
public class UserPanelController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private Validator validator;

    @PostMapping("/address")
    public ResponseEntity saveAddress(@RequestBody Address address) {
        validator.validateAddress(address.getId());

        addressService.saveUserAddress(address);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/address")
    public ResponseEntity updateAddress(@RequestBody Address address) {

        addressService.saveUserAddress(address);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/address")
    public void deleteAddress() {

        addressService.deleteCurrentAddress();
    }
}

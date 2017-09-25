package com.shop.exceptions;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(Long id) {
        super("could not find an Address for a user with id: '" + id + "'.");
    }
}

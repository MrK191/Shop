package com.shop.exceptions;

public class BasketNotFoundException extends RuntimeException {

    public BasketNotFoundException(Long id) {
        super("could not find a basket for a user with id: '" + id + "'.");
    }
}

package com.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BasketNotFoundException extends RuntimeException {

    public BasketNotFoundException(Long id) {
        super("could not find a basket for a user with id: '" + id + "'.");
    }
}

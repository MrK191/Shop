package com.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String bookName) {
        super("could not find a book with name: '" + bookName + "'.");
    }

    public BookNotFoundException(Long id) {
        super("could not find a book with id: '" + id + "'.");
    }
}



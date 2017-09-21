package com.shop.exceptions;

/**
 * Created by Karol on 22.09.2017.
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String bookName) {
        super("could not find a book with name: '" + bookName + "'.");
    }

    public BookNotFoundException(Long id) {
        super("could not find a book with id: '" + id + "'.");
    }
}



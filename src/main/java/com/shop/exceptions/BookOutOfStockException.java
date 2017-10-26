package com.shop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
public class BookOutOfStockException extends RuntimeException {

    public BookOutOfStockException(String bookName) {
        super(bookName + " is out of stock");
    }

}

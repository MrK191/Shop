package com.shop.exceptions;

import com.shop.model.BookCategory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class BooksInBookCategoryNotFoundException extends RuntimeException {

    public BooksInBookCategoryNotFoundException(BookCategory bookCategory) {
        super("could not find any book in : '" + bookCategory.toString() + "' category.");
    }
}

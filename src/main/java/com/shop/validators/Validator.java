package com.shop.validators;

import com.shop.exceptions.AddressNotFoundException;
import com.shop.exceptions.BasketNotFoundException;
import com.shop.exceptions.BookNotFoundException;
import com.shop.exceptions.UserNotFoundException;
import com.shop.service.BookService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Validator {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    public void validateUser(Long userId) {
        Optional.ofNullable(userService.getUserWithId(userId)).orElseThrow(
                () -> new UserNotFoundException(userId));
    }

    public void validateBasket(Long userId) {
        Optional.ofNullable(userService.getBasketWithUserId(userId)).orElseThrow(
                () -> new BasketNotFoundException(userId));
    }

    public void validateAddress(Long userId) {
        Optional.ofNullable(userService.getAddressWithUserId(userId)).orElseThrow(
                () -> new AddressNotFoundException(userId));
    }

    public void validateBookWithBookName(String bookName) {
        Optional.ofNullable(bookService.getBookWithName(bookName)).orElseThrow(
                () -> new BookNotFoundException(bookName));
    }

    public void validateBookWithBookId(Long id) {
        Optional.ofNullable(bookService.getBookWithId(id)).orElseThrow(
                () -> new BookNotFoundException(id));
    }

    public void validateAddressWithUserId(Long userId) {
        Optional.ofNullable(bookService.getBookWithId(userId)).orElseThrow(
                () -> new BookNotFoundException(userId)); //TODO Address service
    }

}

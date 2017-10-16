package com.shop.controllers;

import com.shop.model.Address;
import com.shop.model.Basket;
import com.shop.model.Book;
import com.shop.model.User;
import com.shop.service.BookService;
import com.shop.service.UserService;
import com.shop.validators.Validator;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin-panel")
public class AdminPanel {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @GetMapping("/books")
    List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/books/{id}")
    Book getABookWithId(@PathVariable("id") Long BookId) {
        validator.validateBookWithBookId(BookId);

        return bookService.getBookWithId(BookId);
    }

    @PutMapping("/books/{id}/edit")
    ResponseEntity<Book> editABook(@PathVariable("id") Long BookId,
                                   @RequestBody Book book) throws NotFoundException {
        validator.validateBookWithBookId(BookId);

        bookService.updateABook(book);
        return new ResponseEntity<Book>(HttpStatus.CREATED);
    }

    @PostMapping("/books")
    ResponseEntity<Book> createABook(@RequestBody Book book) {

        if (bookService.isBookInDatabase(book)) {
            return new ResponseEntity<Book>(HttpStatus.CONFLICT);
        }
        bookService.saveABook(book);

        return new ResponseEntity<Book>(HttpStatus.CREATED);
    }

    @DeleteMapping("/books/{id}")
    ResponseEntity<Book> deleteABook(@PathVariable("id") Long BookId) {
        validator.validateBookWithBookId(BookId);

        bookService.deleteBookWithId(BookId);
        return new ResponseEntity<Book>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get-user-with-id/{id}")
    User getUserWithId(@PathVariable("id") Long id) {
        validator.validateUser(id);

        return userService.getUserWithId(id);
    }

    @GetMapping("/users/{id}/basket/")
    Basket getUserBasket(@PathVariable("id") Long userId) {
        validator.validateUser(userId);
        validator.validateBasket(userId);

        return userService.getBasketWithUserId(userId);
    }

    @GetMapping("/users/{id}/address/")
    Address getUserAddress(@PathVariable("id") Long id) {
        validator.validateUser(id);
        validator.validateBasket(id); //TODO validate address

        return userService.getAddressWithUserId(id);
    }

    @GetMapping("/users/{id}/books/")
    List<Book> getUserListOfBooksInBasket(@PathVariable("id") Long id) {
        validator.validateUser(id);

        return bookService.getBooksWithUserId(id, userService);
    }

}

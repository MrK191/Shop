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

    @PostMapping("/books")
    ResponseEntity<Book> createBook(@RequestBody Book book) {

        if (bookService.isBookInDatabase(book)) {
            return new ResponseEntity<Book>(HttpStatus.CONFLICT);
        }
        bookService.saveBook(book);

        return new ResponseEntity<Book>(HttpStatus.CREATED);
    }

    @GetMapping("/books/{id}")
    Book getBookWithId(@PathVariable("id") Long bookId) {
        validator.validateBookWithBookId(bookId);

        return bookService.getBookWithId(bookId);
    }

    @DeleteMapping("/books/{id}")
    ResponseEntity<Book> deleteABook(@PathVariable("id") Long bookId) {
        validator.validateBookWithBookId(bookId);

        bookService.deleteBookWithId(bookId);
        return new ResponseEntity<Book>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/books/{id}/edit")
    ResponseEntity<Book> editABook(@PathVariable("id") Long bookId,
                                   @RequestBody Book book) throws NotFoundException {
        validator.validateBookWithBookId(bookId);

        bookService.updateBook(book);
        return new ResponseEntity<Book>(HttpStatus.CREATED);
    }

    @GetMapping("/users")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users{id}")
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

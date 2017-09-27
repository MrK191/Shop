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

    @GetMapping("/get-all-books")
    List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/book-id/{id}")
    Book getABookWithId(@PathVariable("id") Long BookId) {
        validator.validateBookWithBookId(BookId);

        return bookService.getBookWithId(BookId);
    }

    @GetMapping("/book-name/{BookName}")
    Book getABookWithBookName(@PathVariable("BookName") String bookName) {
        validator.validateBookWithBookName(bookName);

        return bookService.getBookWithName(bookName);
    }

    @PutMapping("/edit-book/{book}")
    ResponseEntity<Book> editABook(@RequestBody Book book) throws NotFoundException {
        validator.validateBookWithBookName(book.getBookName());

        bookService.updateABook(book);
        return new ResponseEntity<Book>(HttpStatus.CREATED);
    }

    @PostMapping("/create-book")
    ResponseEntity<Book> createABook(@RequestBody Book book) {

        if (bookService.isBookInDatabase(book)) {
            return new ResponseEntity<Book>(HttpStatus.CONFLICT);
        }
        bookService.saveABook(book);

        return new ResponseEntity<Book>(HttpStatus.CREATED);
    }

    @PostMapping("/delete-book")
    ResponseEntity<Book> deleteABookWithName(@RequestBody Book book) {
        validator.validateBookWithBookName(book.getBookName());

        bookService.deleteBookWithName(book);
        return new ResponseEntity<Book>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-users")
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get-user-with-id/{id}")
    User getUserWithId(@PathVariable("id") Long id) {
        validator.validateUser(id);

        return userService.getUserWithId(id);
    }

    @GetMapping("/get-user-with-id/{id}/basket/")
    Basket getUserBasket(@PathVariable("id") Long id) {
        validator.validateUser(id);
        validator.validateBasket(id);

        return userService.getBasketWithUserId(id);
    }

    @GetMapping("/get-user-with-id/{id}/address/")
    Address getUserAddress(@PathVariable("id") Long id) {
        validator.validateUser(id);
        validator.validateBasket(id); //TODO validate address

        return userService.getAddressWithUserId(id);
    }

    @GetMapping("/get-user-with-id/{id}/books/")
    List<Book> getUserListOfBooksInBasket(@PathVariable("id") Long id) {
        validator.validateUser(id);

        return bookService.getBooksWithUserId(id, userService);
    }

}

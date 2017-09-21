package com.shop.controllers;

import com.shop.exceptions.BookNotFoundException;
import com.shop.exceptions.UserNotFoundException;
import com.shop.model.Book;
import com.shop.model.User;
import com.shop.service.BookService;
import com.shop.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin-panel")
public class AdminPanel {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @GetMapping("/get-all-books")
    List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/book-id/{id}")
    Book getABookWithId(@PathVariable("id") Long BookId){
        this.validateBookWithBookId(BookId);

      return bookService.getBookWithId(BookId);
    }

    @GetMapping("/book-name/{BookName}")
    Book getABookWithBookName(@PathVariable("BookName") String bookName){
        this.validateBookWithBookName(bookName);

        return bookService.getBookWithName(bookName);
    }

    @PutMapping("/edit-book/{book}")
    ResponseEntity<Book> editABook(@RequestBody Book book) throws NotFoundException {
            this.validateBookWithBookName(book.getBookName());

            bookService.updateABook(book);
            return new ResponseEntity<Book>(HttpStatus.CREATED);
    }

    @PostMapping("/create-book")
    ResponseEntity<Book> createABook(@RequestBody Book book){

        if (bookService.isBookInDatabase(book)){
            return new ResponseEntity<Book>(HttpStatus.CONFLICT);
        }
        bookService.saveABook(book);

        return new ResponseEntity<Book>(HttpStatus.CREATED);
    }

    @PostMapping("/delete-book")
    ResponseEntity<Book> deleteABookWithName(@RequestBody Book book){
            this.validateBookWithBookName(book.getBookName());

            bookService.deleteBookWithName(book);
            return new ResponseEntity<Book>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all-users")
    List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/get-user-with-id/{id}")
    User getUserWithId(@PathVariable("id") Long id ){
        this.validateUser(id);

        return userService.getUserWithId(id);
    }

    private void validateUser(Long userId) {
        Optional.ofNullable(userService.getUserWithId(userId)).orElseThrow(
                () -> new UserNotFoundException(userId));
    }

    private void validateBookWithBookName(String bookName) {
        Optional.ofNullable(bookService.getBookWithName(bookName)).orElseThrow(
                () -> new BookNotFoundException(bookName));
    }

    private void validateBookWithBookId(Long id) {
        Optional.ofNullable(bookService.getBookWithId(id)).orElseThrow(
                () -> new BookNotFoundException(id));
    }
}

package com.shop.controllers;

import com.shop.model.Book;
import com.shop.model.BookCategory;
import com.shop.service.BasketService;
import com.shop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/shop")
public class ShopPageController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BasketService basketService;

    @GetMapping
    private List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @RequestMapping(params = "bookCategory")
    private List<Book> getAllBooksByCategory(@RequestParam(value = "bookCategory") BookCategory bookCategory) {

        return bookService.getBooksWithBookCategory(bookCategory);
    }

    @RequestMapping(params = "bookName")
    private List<Book> getAllBooksWithBookName(@RequestParam(value = "bookName") String bookName) {

        return bookService.getBooksContainingBookName(bookName);
    }

    @PostMapping("/add-book/{bookId}")
    private ResponseEntity<Book> addBookToBasket(@PathVariable Long bookId) {

        basketService.addBookToBasketWithBookId(bookId);

        return new ResponseEntity<Book>(HttpStatus.ACCEPTED);
    }
}

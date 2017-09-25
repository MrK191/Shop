package com.shop.controllers;

import com.shop.model.Book;
import com.shop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class Shop {

    @Autowired
    BookService bookService;

    @GetMapping
    List<Book> books() {
        return bookService.getAllBooks();
    }
}

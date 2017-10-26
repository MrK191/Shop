package com.shop.service;

import com.shop.model.Basket;
import com.shop.model.Book;
import com.shop.model.BookCategory;
import com.shop.repositories.BookRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book updateBook(Book book) throws NotFoundException {
        Book bookFromDatabase = this.getBookWithId(book.getId());

        Book newBook = Book.builder().id(bookFromDatabase.getId()).bookName(book.getBookName()).bookCategory(book.getBookCategory())
                .bookPrice(book.getBookPrice()).unitInStock(book.getUnitInStock()).build();
        bookRepository.save(newBook);
        return book;

    }

    public Book saveBook(Book book) {
        bookRepository.save(book);
        return book;
    }

    public void decreaseBookStock(Book book) {

        book.setUnitInStock(book.getUnitInStock() - 1);
    }

    public void deleteBookWithId(Long BookId) {
        bookRepository.delete(BookId);
    }

    public List<Book> getBooksWithUserId(Long id, UserService userService) {
        Basket basket = userService.getBasketWithUserId(id);

        return basket.getBooks();
    }

    public List<Book> getBooksContainingBookName(String bookName) {
        return bookRepository.getAllByBookNameContaining(bookName);
    }

    public List<Book> getBooksWithBookCategory(BookCategory bookCategory) {
        return bookRepository.findAllByBookCategory(bookCategory);
    }

    public Book getBookWithId(Long id) {
        return bookRepository.getOne(id);
    }

    public Book getBookWithName(String BookName) {
        return bookRepository.getBookByBookName(BookName);
    }

    public boolean isBookInDatabase(Book book) {
        return bookRepository.getBookByBookName(book.getBookName()) != null;
    }
}

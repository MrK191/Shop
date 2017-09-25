package com.shop.service;

import com.shop.model.Basket;
import com.shop.model.Book;
import com.shop.repositories.BookRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book>getAllBooks(){
        return bookRepository.findAll();
    }

    public Book updateABook(Book book) throws NotFoundException {
        Book bookFromDatabase = this.getBookWithName(book.getBookName());

            Book newBook = Book.builder().id(bookFromDatabase.getId()).bookName(book.getBookName()).bookCategory(book.getBookCategory())
                    .bookPrice(book.getBookPrice()).unitInStock(book.getUnitInStock()).build();
            bookRepository.save(newBook);
            return book;

    }

    public Book saveABook(Book book){
        bookRepository.save(book);
        return book;
    }

    public void deleteBookWithName(Book book){
        bookRepository.deleteByBookName(book.getBookName());
    }

    public List<Book> getBooksWithUserId(Long id, UserService userService) {
        Basket basket = userService.getBasketWithUserId(id);

        return basket.getBooks();
    }

    public Book getBookWithId(Long id){
        return bookRepository.getOne(id);
    }

    public Book getBookWithName(String BookName){
        return bookRepository.getBookByBookName(BookName);
    }

    public boolean isBookInDatabase (Book book){
        return bookRepository.getBookByBookName(book.getBookName()) !=null;
    }
}

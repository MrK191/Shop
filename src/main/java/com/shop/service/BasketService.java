package com.shop.service;

import com.shop.exceptions.BookOutOfStockException;
import com.shop.model.Basket;
import com.shop.model.Book;
import com.shop.model.User;
import com.shop.repositories.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    public void addBookToBasketWithBookId(Long bookId, int bookQuanity) {

        User currentUser = userService.getCurrentLoggedInUser();
        Book bookToAdd = bookService.getBookWithId(bookId);
        boolean bookInBasket = this.bookAlreadyInBasket(currentUser, bookToAdd);

        if (bookToAdd.getUnitInStock() <= 0) {

            throw new BookOutOfStockException(bookToAdd.getBookName() + " is out of stock.");
        }
        if (currentUser.getBasket() == null) {
            this.createNewBasket(bookToAdd, currentUser);

            bookService.decreaseBookStockBy(bookToAdd, 1);
        } else {
            Basket userBasket = this.getCurrentUserBasket(currentUser.getId());

            if (bookInBasket) {

                if (bookQuanity > bookToAdd.getUnitInStock()) {
                    throw new BookOutOfStockException("Order quanity: " + bookQuanity +
                        " is bigger than books in stock: " + bookToAdd.getUnitInStock());
                }

                bookService.decreaseBookStockBy(bookToAdd, bookQuanity);
                userBasket.setQuanity(userBasket.getQuanity() + bookQuanity);
                userBasket.setTotalPrice(userBasket.getTotalPrice() + bookToAdd.getBookPrice() * bookQuanity);
            } else {
                this.addBookToBasket(bookToAdd, userBasket);

                bookService.decreaseBookStockBy(bookToAdd, 1);
            }
            basketRepository.save(userBasket);
        }
    }

    public Basket getCurrentUserBasket(Long userId) {
        return basketRepository.getById(userId);
    }

    private void createNewBasket(Book book, User user) {
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(book);

        Basket basket =
            Basket.builder()
                .books(bookList)
                .quanity(1)
                .totalPrice(book.getBookPrice())
                .user(user)
                .build();
    }

    private void addBookToBasket(Book bookToAdd, Basket basket) {

        List<Book> books = basket.getBooks();
        books.add(bookToAdd);

        basket.setBooks(books);
        basket.setQuanity(basket.getQuanity() + 1);
        basket.setTotalPrice(basket.getTotalPrice() + bookToAdd.getBookPrice());

    }

    private boolean bookAlreadyInBasket(User user, Book bookToAdd) {
        Basket userBasket = user.getBasket();
        List<Book> bookList = userBasket.getBooks();

        return bookList.stream().anyMatch(book -> bookToAdd.equals(book));
    }
}

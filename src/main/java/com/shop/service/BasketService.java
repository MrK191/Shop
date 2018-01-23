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

        if (bookToAdd.getUnitInStock() <= 0) {
            throw new BookOutOfStockException(bookToAdd.getBookName() + " is out of stock.");
        }

        if (this.getCurrentBasket(currentUser.getId()) == null) { 
            this.createNewBasket(bookToAdd, currentUser);
            bookService.decreaseBookStockBy(bookToAdd, 1);

        } else {
            Basket userBasket = this.getCurrentBasket(currentUser.getId());

            if (this.bookAlreadyInBasket(currentUser, bookToAdd)) {
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

    public Basket getUserBaskets(Long userId) {
        return basketRepository.findOne(userId);
    }

    public void deleteUserBasket(Long userId) {
        basketRepository.delete(userId);
    }

    public Basket getCurrentBasket(Long userId) {
        return basketRepository.findByCurrentBasket(userId);
    }

    public void deactivateCurrentBasket(Long userId) {
        Basket basket = this.getCurrentBasket(userId);
        basket.setCurrentBasket(false);

        basketRepository.save(basket);
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
                .currentBasket(true)
                .build();

        basketRepository.save(basket);
    }

    private void addBookToBasket(Book bookToAdd, Basket basket) {

        List<Book> books = basket.getBooks();
        books.add(bookToAdd);

        basket.setBooks(books);
        basket.setQuanity(basket.getQuanity() + 1);
        basket.setTotalPrice(basket.getTotalPrice() + bookToAdd.getBookPrice());

    }

    private boolean bookAlreadyInBasket(User user, Book bookToAdd) {
        Basket userBasket = getCurrentBasket(user.getId());
        List<Book> bookList = userBasket.getBooks();

        return bookList.stream().anyMatch(book -> bookToAdd.equals(book));
    }
}

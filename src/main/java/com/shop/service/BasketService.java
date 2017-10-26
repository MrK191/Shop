package com.shop.service;

import com.shop.exceptions.BookOutOfStockException;
import com.shop.model.Basket;
import com.shop.model.Book;
import com.shop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class BasketService {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    public void addBookToBasketWithBookId(Long bookId) {

        User currentUser = userService.getCurrentLoggedInUser();
        Book bookToAdd = bookService.getBookWithId(bookId);

        if (bookToAdd.getUnitInStock() <= 0) {

            throw new BookOutOfStockException(bookToAdd.getBookName());
        } else if (currentUser.getBasket() == null) {

            ArrayList<Book> bookList = new ArrayList<>();
            bookList.add(bookToAdd);

            Basket basket = Basket.builder().books(bookList)
                    .quanity(1)
                    .totalPrice(bookToAdd.getBookPrice())
                    .user(currentUser)
                    .build();

            bookService.decreaseBookStock(bookToAdd);
        } else {

            Basket userBasket = this.getCurrentUserBasket(currentUser);
            userBasket.addBook(bookToAdd);
            userBasket.setQuanity(userBasket.getQuanity() + 1);
            userBasket.setTotalPrice(userBasket.getTotalPrice() + bookToAdd.getBookPrice());

            bookService.decreaseBookStock(bookToAdd);

        }

    }

    public Basket getCurrentUserBasket(User user) {
        return user.getBasket();
    }
}

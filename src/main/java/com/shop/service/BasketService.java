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

            Basket userBasket = this.getCurrentUserBasket(currentUser.getId());
            List<Book> books = userBasket.getBooks();
            books.add(bookToAdd);

            userBasket.setBooks(books);
            userBasket.setQuanity(userBasket.getQuanity() + 1);
            userBasket.setTotalPrice(userBasket.getTotalPrice() + bookToAdd.getBookPrice());

            bookService.decreaseBookStock(bookToAdd);

        }

    }

    public Basket getCurrentUserBasket(Long userId) {
        return basketRepository.getById(userId);
    }
}

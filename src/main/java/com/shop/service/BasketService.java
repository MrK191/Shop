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
            System.out.println("TUUUUU");
            ArrayList<Book> bookList = new ArrayList<>();
            bookList.add(bookToAdd);

            Basket basket =
                Basket.builder()
                    .books(bookList)
                    .quanity(1)
                    .totalPrice(bookToAdd.getBookPrice())
                    .user(currentUser)
                    .build();

            bookService.decreaseBookStockBy(bookToAdd, 1);

            basketRepository.save(basket);
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

                List<Book> books = userBasket.getBooks();
                books.add(bookToAdd);

                userBasket.setBooks(books);
                userBasket.setQuanity(userBasket.getQuanity() + 1);
                userBasket.setTotalPrice(userBasket.getTotalPrice() + bookToAdd.getBookPrice());

                bookService.decreaseBookStockBy(bookToAdd, 1);
            }
            basketRepository.save(userBasket);
        }
    }

    public Basket getCurrentUserBasket(Long userId) {
        return basketRepository.getById(userId);
    }

    private boolean bookAlreadyInBasket(User user, Book bookToAdd) {
        Basket userBasket = user.getBasket();
        List<Book> bookList = userBasket.getBooks();

        return bookList.stream().anyMatch(book -> bookToAdd.equals(book));
    }
}

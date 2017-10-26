package com.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Basket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany
    private List<Book> books;

    @Column(name = "quanity")
    private int quanity;

    @Column(name = "totalPrice")
    private double totalPrice;

    public void addBook(Book book) {
        this.books.add(book);
    }
}

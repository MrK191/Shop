package com.shop.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Basket")
@Data
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany
    private List<Product> product;

    @Column(name = "quanity")
    private int quanity;

    @Column(name = "totalPrice")
    private double totalPrice;
}

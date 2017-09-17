package com.shop.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Basket basket;

    @NotEmpty
    private String productName;

    @NotEmpty
    private String productPrice;

    @Min(value = 0)
    private int unitInStock;
}

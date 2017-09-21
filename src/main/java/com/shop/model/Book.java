package com.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "Books")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Basket basket;

    @NotEmpty
    @Column(unique = true)
    private String bookName;

    @NotEmpty
    private String bookPrice;

    @Min(value = 0)
    private int unitInStock;

    @NotEmpty
    BookCategory bookCategory;
}

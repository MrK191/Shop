package com.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Users2")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    @Size(min=5, max=50)
    private String username;

    @Column(name = "password")
    @Size(min=6, max=50)
    private String password;

    @Column(name = "role")
    private String role;

    @OneToOne
    @Builder.Default
    private Address address;

    @OneToOne
    @Builder.Default
    private Basket basket;

}
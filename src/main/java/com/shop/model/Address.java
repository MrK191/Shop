package com.shop.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Address")
@Data
public class Address {

    @Id
    @Column(name = "addressId")
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "streetName")
    private String streetName;

    @Column(name = "apartmentNumber")
    private String apartmentNumber;

    @Column(name = "postalCode")
    private String postalCode;

    @Column(name = "province")
    private String province;
}

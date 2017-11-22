package com.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CreditCards")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id")
    @MapsId
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CreditCardType creditCardType;

    @NotNull
    private int cardNumber;

    @NotNull
    private int expirationMonth;

    @NotNull
    private int expirationYear;

    @NotNull
    private String caldHolderName;

    @NotNull
    private String caldHolderSurename;

    @Length(min = 3, max = 3)
    private int ccv;

}

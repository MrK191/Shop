package com.shop.repositories;

import com.shop.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BasketRepository extends JpaRepository<Basket, Long> {

    @Query("select c from Basket c where c.currentBasket=true and c.user.id = :userid")
    public Basket findByCurrentBasket(@Param("userid") Long userId);
}

package com.shop.repositories;


import com.shop.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
    public Address deleteById(Long addressId);
}

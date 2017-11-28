package com.shop.repositories;


import com.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);

    public List<User> getAllBy();

    public void removeByUsername(String username);

    @Query(value = "SELECT ad.user FROM Address ad")
    public List<User> getAllUsers();

}

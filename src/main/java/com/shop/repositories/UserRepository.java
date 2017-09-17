package com.shop.repositories;


import com.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    List<User>getAllBy();
    void removeByUsername(String username);
}

package com.shop.repositories;


import com.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByUsername(String username);

    public List<User> getAllBy();

    public void removeByUsername(String username);

}

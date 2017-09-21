package com.shop.repositories;

import com.shop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long> {

   public List<Book> getAllBy();
   public Book getBookByBookName(String BookName);
   public Book deleteByBookName (String BookName);
}
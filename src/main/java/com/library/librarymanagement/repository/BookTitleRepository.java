package com.library.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.BookTitleImagePath;
import com.library.librarymanagement.entity.BookTypeImagePath;

@Repository
public interface BookTitleRepository extends JpaRepository<BookTitleImagePath, Integer> {
    boolean existsByName(final String name); 
    public List<BookTitleImagePath> findByType(BookTypeImagePath type); 
    public List<BookTitleImagePath> findByAuthor(String author);  
    public BookTitleImagePath findByName(String name);
   
}

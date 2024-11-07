package com.library.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.entity.BookTitleImagePath;

@Repository
public interface BookRepository extends JpaRepository<BookImagePath, Integer> { 
    public List<BookImagePath> findByTitle(BookTitleImagePath bookTitleImagePath);
}

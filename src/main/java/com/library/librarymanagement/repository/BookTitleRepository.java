package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.BookTitleImagePath;

@Repository
public interface BookTitleRepository extends JpaRepository<BookTitleImagePath, Integer> {
    boolean existsByName(final String name);
}

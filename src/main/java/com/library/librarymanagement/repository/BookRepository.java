package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.BookImagePath;

@Repository
public interface BookRepository extends JpaRepository<BookImagePath, Integer> {
}

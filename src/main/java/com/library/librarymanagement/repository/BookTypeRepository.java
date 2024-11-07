package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.BookTypeImagePath;

@Repository
public interface BookTypeRepository extends JpaRepository<BookTypeImagePath, Short> {
    boolean existsByName(final String name);

    boolean existsByImagePath(final String imagePath);
}

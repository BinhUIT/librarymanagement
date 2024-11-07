package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.BookStatus;

@Repository
public interface BookStatusRepository extends JpaRepository<BookStatus, Byte> {
}

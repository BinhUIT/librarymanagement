package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.librarymanagement.entity.ReturningCard;

public interface ReturningCardRepository extends JpaRepository<ReturningCard, Integer> {

}

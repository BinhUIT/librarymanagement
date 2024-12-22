package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.Penalty;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Integer> {
    

}

package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.BorrowingCardDetail;

@Repository
public interface BorrowingCardDetailRepository extends JpaRepository<BorrowingCardDetail, Integer>{

}

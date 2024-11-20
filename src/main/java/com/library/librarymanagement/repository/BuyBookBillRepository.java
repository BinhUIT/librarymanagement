package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.BuyBookBill;

@Repository
public interface BuyBookBillRepository extends JpaRepository<BuyBookBill, Integer> { 

}

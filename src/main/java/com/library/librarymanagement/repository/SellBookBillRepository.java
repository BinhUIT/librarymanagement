package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.SellBookBill;

@Repository
public interface SellBookBillRepository extends JpaRepository<SellBookBill, Integer> {

}

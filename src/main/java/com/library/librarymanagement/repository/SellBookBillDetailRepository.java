package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.SellBookBillDetail;

@Repository
public interface SellBookBillDetailRepository extends JpaRepository<SellBookBillDetail, Integer> {

}

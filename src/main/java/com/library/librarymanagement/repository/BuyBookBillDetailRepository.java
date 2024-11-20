package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.BuyBookBillDetail;

@Repository
public interface BuyBookBillDetailRepository extends JpaRepository<BuyBookBillDetail, Integer> {

}

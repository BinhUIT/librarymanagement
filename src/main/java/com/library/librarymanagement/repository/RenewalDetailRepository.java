package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.RenewalCardDetail;

@Repository 
public interface  RenewalDetailRepository extends JpaRepository<RenewalCardDetail, Integer> {

}

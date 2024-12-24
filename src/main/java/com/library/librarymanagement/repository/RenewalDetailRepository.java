package com.library.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.RenewalCardDetail;
import com.library.librarymanagement.entity.RenewalDetail;

@Repository 
public interface  RenewalDetailRepository extends JpaRepository<RenewalDetail, Integer> {
    public List<RenewalDetail> findByStatus(int status);
}

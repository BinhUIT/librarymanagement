package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.ReturningCardDetail;

@Repository
public interface  ReturningCardDetailRpository  extends JpaRepository<ReturningCardDetail, Integer>{

}

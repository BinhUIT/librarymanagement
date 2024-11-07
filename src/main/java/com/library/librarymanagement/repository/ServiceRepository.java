package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.Service;
@Repository
public interface  ServiceRepository extends JpaRepository<Service, Integer> {

}

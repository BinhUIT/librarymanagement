package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.ServiceType;
@Repository
public interface  ServiceTypeRepository extends JpaRepository<ServiceType, Integer> {

}

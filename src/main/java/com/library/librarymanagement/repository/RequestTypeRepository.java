package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.RequestType;

@Repository
public interface RequestTypeRepository extends JpaRepository<RequestType, Integer> {
    public RequestType findByName(String name);


}

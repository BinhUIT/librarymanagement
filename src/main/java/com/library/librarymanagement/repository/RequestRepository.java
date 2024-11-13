package com.library.librarymanagement.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.Request; 
@Repository
public interface RequestRepository extends  JpaRepository<Request, Integer> 
{

}

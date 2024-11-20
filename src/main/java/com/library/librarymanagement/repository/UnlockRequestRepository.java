package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.UnlockRequest;

@Repository
public interface UnlockRequestRepository extends JpaRepository<UnlockRequest, Integer> { 


}

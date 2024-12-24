package com.library.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.Penalty;
import com.library.librarymanagement.entity.User;

@Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Integer> { 
    public List<Penalty>  findByReader(User reader);
    

}

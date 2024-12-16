package com.library.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.entity.WorkDetail;
import java.util.Date;
@Repository

public interface WorkDetailRepository extends JpaRepository<WorkDetail, Integer> { 
    public List<WorkDetail> findByLibrarian(User librarian); 
    public List<WorkDetail> findByWorkDate(Date workDate);

}

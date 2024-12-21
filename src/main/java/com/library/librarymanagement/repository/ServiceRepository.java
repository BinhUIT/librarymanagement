package com.library.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.Service;
import com.library.librarymanagement.entity.ServiceType;
import com.library.librarymanagement.entity.User;
@Repository
public interface  ServiceRepository extends JpaRepository<Service, Integer> { 
    public List<Service>  findByServiceType(ServiceType serviceType); 
    public List<Service> findByReader(User reader);

}

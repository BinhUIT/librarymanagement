package com.library.librarymanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{ 
    public User findByFullName(String fullName); 
    public User findByVerificationCode(String verificationCode); 

}

package com.library.librarymanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.library.librarymanagement.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer>{ 
    public User findByEmail(String email); 
    public User findByFullName(String fullname); 
    public User findByVerificationCode(String verificationCode);
    public List<User> findByRole(int role);

}

package com.library.librarymanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.security.TokenSecurity;

@Service
public class AdminService { 
    @Autowired 
    private UserRepository userRepo; 

    @Autowired 
    private TokenSecurity tokenSecurity; 

    public ResponseEntity<String> lockOrUnlockLibrarian(int librarianId, boolean lockOrUnlock) 
    {  
        User user= userRepo.findById(librarianId).orElse(null); 
        if(user==null) {
            return new ResponseEntity<>("Not found librarian", HttpStatus.NOT_FOUND);
        } 
        if(user.getRole()!=1) 
        { 
            return new ResponseEntity<>("This is not librarian", HttpStatus.BAD_REQUEST);
        } 
        user.setEnable(lockOrUnlock);

        return new ResponseEntity<>("Action complete", HttpStatus.OK);
    }  

    public ResponseEntity<String> librarianWork(int librarianId) 
    { 
        User librarian= userRepo.findById(librarianId).orElse(null); 
        
        if(librarian==null)
        {
            return new ResponseEntity<>("Librarian not found", HttpStatus.NOT_FOUND);
        } 
        return null;
    }
    
}

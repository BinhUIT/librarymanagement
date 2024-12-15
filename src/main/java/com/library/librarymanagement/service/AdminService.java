package com.library.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.request.RegisterRequest;
import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.securitySalt.BcryptSalt;

@Service
public class AdminService { 
    @Autowired 
    private UserRepository userRepo; 

    @Autowired 
    private TokenSecurity tokenSecurity;  
    @Autowired 
    private BcryptSalt bcryptSalt;

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

    public List<User> getAllLibrarian() 
    {
        List<User> listRes= new ArrayList<>();
        List<User> listUser= userRepo.findAll();
        for(int i=0;i<listUser.size();i++) 
        {
            if(listUser.get(i).getRole()==1) 
            {
                listRes.add(listUser.get(i));
            }
        } 
        return listRes;
    }

    public ResponseEntity<User> createLibrarian(RegisterRequest request) 
    {
        User user= userRepo.findByFullName(request.getFullName());
        if(user!=null) 
        {
            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        } 
        user= userRepo.findByEmail(request.getEmail());
        if(user!=null) 
        {
            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        } 
        User newUser= new User(request);
        int newId= userRepo.findAll().size();
        newUser.setUserId(newId);
        newUser.setPassword(BCrypt.hashpw(request.getPassword(), bcryptSalt.getSalt())); 
        newUser.setRole(1); 
        newUser.setEnable(true);
        userRepo.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    public ResponseEntity<String> lockLibrarian(int librarianId) 
    {
        User librarian= userRepo.findById(librarianId).orElse(null); 
        if(librarian==null||librarian.getEnable()==false||librarian.getRole()<1) 
        {
            return new ResponseEntity<>("Can not delete", HttpStatus.BAD_REQUEST);
        } 
        librarian.setEnable(false);
        userRepo.save(librarian);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    } 


    public ResponseEntity<String> unlockLibrarian(int librarianId)
    {
        User librarian= userRepo.findById(librarianId).orElse(null); 
        if(librarian==null||librarian.getEnable()==true||librarian.getRole()<1) 
        {
            return new ResponseEntity<>("Can not unlock", HttpStatus.BAD_REQUEST);
        } 
        librarian.setEnable(true);
        userRepo.save(librarian);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    
}

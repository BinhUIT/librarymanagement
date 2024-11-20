package com.library.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.service.AdminService;

@RestController
public class AdminController { 
    @Autowired 
    private AdminService adminService; 

    @Autowired 
    private TokenSecurity tokenSecurity;

    @PostMapping("/admin/lock_or_unlock/librarian/{id}/{isUnlock}") 
    public ResponseEntity<String> lockLibrarian(@RequestHeader("Authorization") String authHeader,@PathVariable int id, @PathVariable boolean isUnlock  ) 
    {  
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))||tokenSecurity.extractRole(authHeader)<=1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);

        } 

        return adminService.lockOrUnlockLibrarian(id, isUnlock);
        
    }

}

package com.library.librarymanagement.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.request.RegisterRequest;
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

    @GetMapping("/admin/getAllLinrarian") 
    public List<User> getAllLibrarian(@RequestHeader("Authorization") String authHeader) 
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))||tokenSecurity.extractRole(authHeader)<=1) 
        {
            return null;
        } 
        return adminService.getAllLibrarian();
    } 

    @PostMapping("/admin/createLibrarian") 
    public ResponseEntity<User> createLibrarian(@RequestHeader("Authorization") String authHeader, @RequestBody RegisterRequest request) 
    {
        
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))||tokenSecurity.extractRole(authHeader)<=1) 
        {
            return null;
        }  
        return adminService.createLibrarian(request);
    }  

    @PutMapping("/admin/lock/librarian/{id}") 
    public ResponseEntity<String> lockLibrarian(@RequestHeader("Authorization") String authHeader, @PathVariable int id) 
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))||tokenSecurity.extractRole(authHeader)<=1) 
        {
            return null;
        } 
        return adminService.lockLibrarian(id);
    } 

    @PutMapping("/admin/unlock/librarian/{id}") 
    public ResponseEntity<String> unlockLibrarian(@RequestHeader("Authorization") String authHeader, @PathVariable int id) 
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))||tokenSecurity.extractRole(authHeader)<=1) 
        {
            return null;
        } 
        return adminService.unlockLibrarian(id);
    }

}

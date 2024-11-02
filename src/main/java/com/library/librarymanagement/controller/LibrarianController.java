package com.library.librarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.entity.Request;
import com.library.librarymanagement.request.LibrarianResponse;
import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.service.LibrarianService;

@RestController
public class LibrarianController { 
    @Autowired 
    private LibrarianService librarianService;

    @Autowired 
    private TokenSecurity tokenSecurity;  
    @GetMapping("/librarian/uncheck_request")
    public ResponseEntity<List<Request>> getUnCheckRequest(@RequestHeader("Authorization") String authHeader) 
    {  
        if(authHeader==null||!authHeader.startsWith("Bearer ")) 
        { 
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); 

        }
        String token = authHeader.substring(7);  
        if(tokenSecurity.validateToken(token)) return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        int role= tokenSecurity.extractRole(token); 
        if(role<1) return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED); 

        return librarianService.getAllUnCheckRequest();
    } 

    @PostMapping("/librarian/check_request") 
    public ResponseEntity<String> checkRequest(@RequestHeader("Authorization") String authHeader, @RequestBody LibrarianResponse response) 
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ")) 
        { 
            return new ResponseEntity<>("You have no permission", HttpStatus.BAD_REQUEST); 

        }
        String token = authHeader.substring(7);  
        if(tokenSecurity.validateToken(token)) return new ResponseEntity<>("You have no permission",HttpStatus.UNAUTHORIZED);
        int role= tokenSecurity.extractRole(token); 
        if(role<1) return new ResponseEntity<>("You have no permission",HttpStatus.UNAUTHORIZED);  
        return librarianService.ResponseRequest(response);
    }

}

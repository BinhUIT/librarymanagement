package com.library.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.request.BorrowingRequest;
import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.service.ReaderService;

@RestController
public class ReaderController { 
    @Autowired 
    private ReaderService readerService;

    @Autowired 
    private TokenSecurity tokenSecurity;

    @PostMapping("/reader/send/borrowing_request")  
    public ResponseEntity<String> sendBorrowingRequest(@RequestHeader("Authorization") String authHeader, @RequestBody BorrowingRequest request) 
    { 
        if(authHeader==null||!authHeader.startsWith("Bearer ")) 
        { 
            return new ResponseEntity<>("Fail to send request", HttpStatus.BAD_REQUEST); 

        }
        String token = authHeader.substring(7);  
        if(tokenSecurity.validateToken(token)) return new ResponseEntity<>("You have no permission", HttpStatus.UNAUTHORIZED);

        return readerService.sendBorrowingRequest(request);
    }

}

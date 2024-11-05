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

import com.library.librarymanagement.entity.Notification;
import com.library.librarymanagement.request.BorrowingRequest;
import com.library.librarymanagement.request.GetNotificationRequest;
import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.service.ReaaderService;


@RestController
public class ReaderController { 
    @Autowired 
    private ReaaderService readerService; 

    @Autowired 
    private TokenSecurity tokenSecurity; 

    @PostMapping("/reader/borrow") 
    public ResponseEntity<String> borrowBook(@RequestHeader("Authorization") String authHeader , @RequestBody BorrowingRequest request)
    { 
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.validateToken(authHeader.substring(7))) 
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED); 

        return readerService.borrowingBook(request);
    }  

    @SuppressWarnings("null")
    @GetMapping("/reader/allNotifications") 
    public ResponseEntity<List<Notification>> getAllNotification(@RequestHeader("Authorization") String authHeader )
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.validateToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7)); 
        return readerService.getAllNotification(userId);
    } 

    @SuppressWarnings("null") 
    @GetMapping("/reader/unreadNotifications") 
    public ResponseEntity<List<Notification>> getUnreadNotification(@RequestHeader("Authorization") String authHeader) 
    { 
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.validateToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7)); 
        return readerService.getUnreadNotification(userId);
    } 

    @SuppressWarnings("null") 
    @GetMapping("/reader/readANotification") 
    public ResponseEntity<Notification> readANotification(@RequestHeader("Authorization") String authHeader, @RequestBody GetNotificationRequest request) 
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.validateToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7)); 
        return readerService.readANotification(userId, request.getNotifId());
    }
   

}

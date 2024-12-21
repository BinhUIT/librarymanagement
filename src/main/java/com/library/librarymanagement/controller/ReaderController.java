package com.library.librarymanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.entity.BorrowingCardDetail;
import com.library.librarymanagement.entity.CartDetail;
import com.library.librarymanagement.entity.Notification;
import com.library.librarymanagement.request.AddCartDetailRequest;
import com.library.librarymanagement.request.BorrowingRequest;
import com.library.librarymanagement.request.CartDetailUpdateRequest;
import com.library.librarymanagement.request.GetNotificationRequest;
import com.library.librarymanagement.request.RenewalRequest;
import com.library.librarymanagement.response.BorrowResponse;
import com.library.librarymanagement.response.CartDetailResponse;
import com.library.librarymanagement.response.ResponseData;
import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.service.ReaaderService;


@RestController
public class ReaderController { 
    @Autowired 
    private ReaaderService readerService; 

    @Autowired 
    private TokenSecurity tokenSecurity; 

    @PostMapping("/reader/borrow/cart") 
    public ResponseEntity<BorrowResponse> borrowViaCart(@RequestHeader("Authorization") String authHeader, @RequestBody BorrowingRequest request ) 
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7)); 
        return readerService.borrowViaCart(request, userId);
    }
    @SuppressWarnings("null")
    @GetMapping("/reader/allNotifications") 
    public ResponseEntity<List<Notification>> getAllNotification(@RequestHeader("Authorization") String authHeader )
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))) 
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
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))) 
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
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7)); 
        return readerService.readANotification(userId, request.getNotifId());
    } 

    @GetMapping("/reader/cart") 
    public ResponseEntity<List<CartDetailResponse>> getCart(@RequestHeader("Authorization") String authHeader) 
    { 
        //System.out.println(authHeader); 
        //System.out.println(authHeader.substring(7));
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);

        } 
        //System.out.println(authHeader.substring(7)); 
        //return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        int userId = tokenSecurity.extractUserId(authHeader.substring(7));

        return readerService.getCart(userId);
    } 

    @PostMapping("/reader/addToCart") 
    public ResponseEntity<String> addToCart(@RequestHeader("Authorization") String authHeader, @RequestBody AddCartDetailRequest request) 
    { 
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7)); 
        return readerService.addToCart(request, userId);
    } 

    @DeleteMapping("/reader/deleteItemFromCart/{id}") 
    public ResponseEntity<String> deleteFromCart(@RequestHeader("Authorization") String authHeader, @PathVariable int id) 
    { 
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7));  
        System.out.println(userId);
        return readerService.removeFromCart(id, userId);
    }  

    @DeleteMapping("/reader/clearCart") 
    public ResponseEntity<String> clearCart(@RequestHeader("Authorization") String authHeader) 
    { 
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7)); 
        return readerService.clearCart(userId);
    } 

    @PutMapping("/reader/saveCart") 
    public ResponseEntity<List<CartDetailResponse>> updateCart(@RequestHeader("Authorization") String authHeader, @RequestBody List<CartDetailUpdateRequest> request) 
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7));  
        return readerService.updateReaderCart(request, userId);

    } 

    @PostMapping("/reader/renewal") 
    public ResponseEntity<String> sendRenewal(@RequestHeader("Authorization") String authHeader, @RequestBody RenewalRequest request) 
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7)); 
        return readerService.sendRenewalRequest(request, userId);
    } 


    @PostMapping("/reader/send/unlockRequest") 
    public ResponseEntity<String> sendUnlockRequest(@RequestHeader("Authorization") String authHeader) 
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.validateToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);

        } 
          

        return readerService.sendUnlockRequest(authHeader);
    }
   

    @GetMapping("/reader/history") 
    public ResponseEntity<List<BorrowingCardDetail>> getAllBorrowingHistory(@RequestHeader("Authorization") String authHeader) 
    {
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7)); 
        return readerService.getAllBorrowingCardDetail(userId);
    } 


    
   

}

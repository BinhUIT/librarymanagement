package com.library.librarymanagement.controller;

import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.entity.BorrowingCardDetail.Status;
import com.library.librarymanagement.request.UpdateBorrowingCardDetailRequest;
import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.service.BorrowingCardDetailService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("borrowing-card-detail")
public final class BorrowingCardDetailController { 
    @Autowired
    private TokenSecurity tokenSecurity;
    private final BorrowingCardDetailService service;

    private BorrowingCardDetailController(final BorrowingCardDetailService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Boolean> updateStatusBorrowing(@RequestBody @Valid final UpdateBorrowingCardDetailRequest request, @RequestHeader("Authorization") String  authHeader) {
        if(request.getStatus()==Status.BORROWING) 
        {
            int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
            if(userId<=-1) 
            {
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            } 
            if(!tokenSecurity.userExist(userId))  
            { 
                return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
            }
        } 
        else {
             if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.validateToken(authHeader.substring(7))) 
        {
            return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

        } 
        }
        try {
            service.UpdateBorrowingStatus(request);
        } catch (final Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        } 
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }
}

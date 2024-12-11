package com.library.librarymanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.library.librarymanagement.entity.BorrowingCardDetail;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.request.BookTitleCreateRequest;
import com.library.librarymanagement.request.BookTitleUpdateRequest;
import com.library.librarymanagement.request.BookTypeCreateRequest;
import com.library.librarymanagement.request.BuyBookBillRequest;
import com.library.librarymanagement.request.SellBookBillCreateRequest;
import com.library.librarymanagement.request.UnlockResponse;
import com.library.librarymanagement.response.BookBorrowingDetailResponse;
import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.service.LibrarianService;

@RestController
public class LibrarianController { 
    @Autowired 
    private LibrarianService librarianService;

    @Autowired 
    private TokenSecurity tokenSecurity;  
    
    @PostMapping("/librarian/buyBook") 
    public ResponseEntity<String> buyBook(@RequestHeader("Authorization") String authHeader, @RequestBody BuyBookBillRequest request)  
    {  
        if(authHeader==null||!authHeader.startsWith("Bearer ") || !tokenSecurity.checkToken(authHeader.substring(7))||tokenSecurity.extractRole(authHeader)<1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);

        } 
        int userId = tokenSecurity.extractUserId(authHeader.substring(7)); 
        return librarianService.buyBook(request, userId);
    } 
    @PostMapping("/librarian/create/BookTitle/image")  
    public ResponseEntity<String> createNewBookTitleImage(@RequestHeader("Authorization") String authHeader, @RequestParam("file") MultipartFile imageFile) 
    { 
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }

        if(librarianService.createNewBookTitleImage(imageFile)) 
        {
            return new ResponseEntity<>("Create image Success", HttpStatus.OK);
        } 
        else { 
            return new ResponseEntity<>("Create image fail", HttpStatus.BAD_REQUEST);
        }


    } 
    @PostMapping("/librarian/create/BookTitle/info" ) 
    public ResponseEntity<String> createNewBookTitle(@RequestHeader("Authorization") String authHeader, @RequestBody BookTitleCreateRequest request) 
    {  
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        } 
        return librarianService.createNewBookTitle(request);
    }  

    @PostMapping("/librarian/create/BookType/image")  
    public ResponseEntity<String> createNewBookTypeImage(@RequestHeader("Authorization") String authHeader, @RequestParam("file") MultipartFile imageFile) 
    { 
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }

        if(librarianService.createNewBookTypeImage(imageFile)) 
        {
            return new ResponseEntity<>("Create image Success", HttpStatus.OK);
        } 
        else { 
            return new ResponseEntity<>("Create image fail", HttpStatus.BAD_REQUEST);
        }
    }   
    @PostMapping("/librarian/create/BookType/info" ) 
    public ResponseEntity<String> createNewBookType(@RequestHeader("Authorization") String authHeader, @RequestBody BookTypeCreateRequest request) 
    {  
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        } 
        return librarianService.createNewBookType(request);
    } 

    @PostMapping("/librarian/sell/book") 
    public ResponseEntity<String> sellBook(@RequestHeader("Authorization") String authHeader, @RequestBody SellBookBillCreateRequest request) 
    {  
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        } 
        return librarianService.SellBook(request, userId);
    } 

    @PostMapping("/librarian/response/renewal/{id}") 
    public ResponseEntity<String> responseRenewalRequest(@RequestHeader("Authorization") String authHeader,@RequestBody String state, @PathVariable int id ) 
    {  
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        }  
        if(state.equals("true")) 
        {
            return librarianService.responseARenewalRequest(id, true, userId); 

        } 
        if(state.equals("false")) 
        { 
            return librarianService.responseARenewalRequest(id, false, userId);
        } 
        return new ResponseEntity<>("Wrong information", HttpStatus.BAD_REQUEST);
       
    } 

    @PostMapping("/librarian/lock/{userId}") 
    public ResponseEntity<User> lockUser(@RequestHeader("Authorization") String authHeader, @PathVariable int userId) 
    { 
        int librarianId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(librarianId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }  
        return librarianService.lockUser(userId);
        
    }  

    @PostMapping("/librarian/response/unlockRequest") 
    public ResponseEntity<String> responseUnlockRequest(@RequestHeader("Authorization") String authHeader, @RequestBody UnlockResponse unlockResponse) 
    { 
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("Denied", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("User does not exist", HttpStatus.BAD_REQUEST);
        } 

        return librarianService.responseUnlockRequest(userId,unlockResponse);
    }

    @GetMapping("/librarian/getAllBorrowing") 
    public ResponseEntity<List<BookBorrowingDetailResponse>> getAllBorrowing(@RequestHeader("Authorization") String authHeader)
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }  
        return librarianService.getAllBorrowingCardDetail();
    } 

    @PutMapping("/librarian/update/bookTitle/image/{id}") 
    public ResponseEntity<String> updateBookTitleImage(@RequestHeader("Authorization") String authHeader, @RequestParam("file") MultipartFile file, @PathVariable int id) 
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("No permission", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("No permission", HttpStatus.BAD_REQUEST);
        }  
        return librarianService.updateBookTitleImage(file,id);
    }

    @PutMapping("/librarian/updates/bookTitle/info") 
    public ResponseEntity<String> updateBookTitleInfo(@RequestHeader("Authorization") String authHeader, @RequestBody BookTitleUpdateRequest request)
    {
        int userId = tokenSecurity.getUserIdAndCheckLibrarian(authHeader);
        if(userId<=-1) 
        {
            return new ResponseEntity<>("No permission", HttpStatus.UNAUTHORIZED);
        } 
        if(!tokenSecurity.userExist(userId))  
        { 
            return new ResponseEntity<>("No permission", HttpStatus.BAD_REQUEST);
        } 
        return librarianService.updateBookTitleInfo(request);
    }








}

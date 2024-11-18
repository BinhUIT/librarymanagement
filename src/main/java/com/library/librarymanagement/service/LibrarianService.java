package com.library.librarymanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.Request;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.RequestRepository;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.request.LibrarianResponse;

@Service
public class LibrarianService { 
    @Autowired 
    private RequestRepository requestRepo; 

    @Autowired 
    private UserRepository userRepo;  

    public ResponseEntity<List<Request>> getAllUnCheckRequest() 
    {
        List<Request> listRequest = requestRepo.findAll(); 
        List<Request> unCheckRequest= new ArrayList<>(); 
        for(Request i :listRequest) 
        { 
            if(i.getLibrarianId()==-1) 
            unCheckRequest.add(i);
        } 
        return new ResponseEntity<>(unCheckRequest, HttpStatus.OK);


    } 

    public ResponseEntity<String> ResponseRequest(LibrarianResponse response) 
    {
        Request request= requestRepo.findById(response.getRequestId()).orElse(null); 
        if(request==null) return new ResponseEntity<>("This request does not exists", HttpStatus.BAD_REQUEST);
        User user = userRepo.findById(response.getLibrarianId()).orElse(null); 
        if(user==null) return new ResponseEntity<>("Wrong librarian info", HttpStatus.UNAUTHORIZED); 
        request.setIsAccept(response.getIsAccept()); 
        request.setLibrarianId(response.getLibrarianId()); 
        requestRepo.save(request); 
        return new ResponseEntity<>("Checked request", HttpStatus.OK);
    }
}

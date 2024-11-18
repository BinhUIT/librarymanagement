package com.library.librarymanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.Request;
import com.library.librarymanagement.entity.RequestType;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.repository.RequestRepository;
import com.library.librarymanagement.repository.RequestTypeRepository;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.request.BorrowingRequest;

@Service
public class ReaderService {

    @Autowired 
    private RequestRepository requestRepo; 

    @Autowired 
    private RequestTypeRepository requestTypeRepo; 

    @Autowired 
    private UserRepository userRepo;

    public ResponseEntity<String> sendBorrowingRequest(BorrowingRequest request) 
    {
        User user= userRepo.findById(request.getReaderId()).orElse(null); 
        if(user==null) return new ResponseEntity<>("User does not exists", HttpStatus.BAD_REQUEST);  

        RequestType requestType = requestTypeRepo.findById(request.getRequestTypeId()).orElse(null); 
        if(requestType==null) return new ResponseEntity<>("Invalid request type", HttpStatus.BAD_REQUEST);

        Request rqst= new Request(request); 
        List<Request> listRequest = requestRepo.findAll(); 
        int newId= listRequest.size(); 
        rqst.setRequestId(newId);
        requestRepo.save(rqst);
        return new ResponseEntity<>("Request has been sent", HttpStatus.OK);

    }
}

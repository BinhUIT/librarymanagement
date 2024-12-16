package com.library.librarymanagement.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.entity.WorkDetail;
import com.library.librarymanagement.repository.UserRepository;
import com.library.librarymanagement.repository.WorkDetailRepository;
import com.library.librarymanagement.request.AddWorkDetail;
import com.library.librarymanagement.request.RegisterRequest;
import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.securitySalt.BcryptSalt;

@Service
public class AdminService { 
    @Autowired 
    private UserRepository userRepo; 

    @Autowired 
    private TokenSecurity tokenSecurity;  
    @Autowired 
    private BcryptSalt bcryptSalt; 

    @Autowired
    private WorkDetailRepository workDetailRepo;

    public ResponseEntity<String> lockOrUnlockLibrarian(int librarianId, boolean lockOrUnlock) 
    {  
        User user= userRepo.findById(librarianId).orElse(null); 
        if(user==null) {
            return new ResponseEntity<>("Not found librarian", HttpStatus.NOT_FOUND);
        } 
        if(user.getRole()!=1) 
        { 
            return new ResponseEntity<>("This is not librarian", HttpStatus.BAD_REQUEST);
        } 
        user.setEnable(lockOrUnlock);

        return new ResponseEntity<>("Action complete", HttpStatus.OK);
    }  

    public ResponseEntity<String> librarianWork(int librarianId) 
    { 
        User librarian= userRepo.findById(librarianId).orElse(null); 
        
        if(librarian==null)
        {
            return new ResponseEntity<>("Librarian not found", HttpStatus.NOT_FOUND);
        } 
        return null;
    }

    public List<User> getAllLibrarian() 
    {
        List<User> listRes= new ArrayList<>();
        List<User> listUser= userRepo.findAll();
        for(int i=0;i<listUser.size();i++) 
        {
            if(listUser.get(i).getRole()==1) 
            {
                listRes.add(listUser.get(i));
            }
        } 
        return listRes;
    }

    public ResponseEntity<User> createLibrarian(RegisterRequest request) 
    {
        User user= userRepo.findByFullName(request.getFullName());
        if(user!=null) 
        {
            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        } 
        user= userRepo.findByEmail(request.getEmail());
        if(user!=null) 
        {
            return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
        } 
        User newUser= new User(request);
        int newId= userRepo.findAll().size();
        newUser.setUserId(newId);
        newUser.setPassword(BCrypt.hashpw(request.getPassword(), bcryptSalt.getSalt())); 
        newUser.setRole(1); 
        newUser.setEnable(true);
        userRepo.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    public ResponseEntity<String> lockLibrarian(int librarianId) 
    {
        User librarian= userRepo.findById(librarianId).orElse(null); 
        if(librarian==null||librarian.getEnable()==false||librarian.getRole()<1) 
        {
            return new ResponseEntity<>("Can not delete", HttpStatus.BAD_REQUEST);
        } 
        librarian.setEnable(false);
        userRepo.save(librarian);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    } 


    public ResponseEntity<String> unlockLibrarian(int librarianId)
    {
        User librarian= userRepo.findById(librarianId).orElse(null); 
        if(librarian==null||librarian.getEnable()==true||librarian.getRole()<1) 
        {
            return new ResponseEntity<>("Can not unlock", HttpStatus.BAD_REQUEST);
        } 
        librarian.setEnable(true);
        userRepo.save(librarian);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    public ResponseEntity<String> createWorkDetail(AddWorkDetail request) 
    {
       
        User librarian = userRepo.findById(request.getLibrarianId()).orElse(null);
        if(librarian==null||librarian.getEnable()==false||librarian.getRole()!=1) 
        {
            return new ResponseEntity<>("This librarian account can not be used", HttpStatus.BAD_REQUEST);
        } 
        List<WorkDetail> listWorkDetails= workDetailRepo.findByWorkDate(request.getWorkDate());
        if(listWorkDetails.size()!=0) 
        {
            return new ResponseEntity<>("There is another lirbrarian work at this day", HttpStatus.BAD_REQUEST);
        }  
        Date currentDate= new Date();
        if(request.getWorkDate().before(currentDate)) 
        {
            return new ResponseEntity<>("Invalid date", HttpStatus.BAD_REQUEST);
        }
        WorkDetail workDetail = new WorkDetail(librarian, request.getWorkDate());
        workDetailRepo.save(workDetail);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
    public ResponseEntity<List<WorkDetail>> getAllWorkDetail() 
    {
        return new ResponseEntity<>(workDetailRepo.findAll(), HttpStatus.OK);
    } 
    public ResponseEntity<List<WorkDetail>> getAllFutureWorkDetail() 
    {
        Date currentDate= new Date();
        List<WorkDetail> listRes= new ArrayList<>();
        List<WorkDetail> listWorkDetail = workDetailRepo.findAll();
        for(int i=0;i<listWorkDetail.size();i++) 
        {
            if(!listWorkDetail.get(i).getWorkDate().before(currentDate)) 
            {
                listRes.add(listWorkDetail.get(i));
            } 
        } 
        return new ResponseEntity<>(listRes, HttpStatus.OK);
    }
    public ResponseEntity<String> updateWorkDate(AddWorkDetail request, int workDetailId) 
    {
        WorkDetail workDetail = workDetailRepo.findById(workDetailId).orElse(null);
        if(workDetail==null)
        {
            return new ResponseEntity<>("Update fail", HttpStatus.BAD_REQUEST);
        }
        User librarian = userRepo.findById(request.getLibrarianId()).orElse(null);
        if(librarian==null||librarian.getEnable()==false||librarian.getRole()!=1) 
        {
            return new ResponseEntity<>("This librarian account can not be used", HttpStatus.BAD_REQUEST);
        } 
        List<WorkDetail> listWorkDetails= workDetailRepo.findByWorkDate(request.getWorkDate());
        if(listWorkDetails.size()!=0) 
        {
            return new ResponseEntity<>("There is another lirbrarian work at this day", HttpStatus.BAD_REQUEST);
        }  
        Date currentDate= new Date();
        if(request.getWorkDate().before(currentDate)) 
        {
            return new ResponseEntity<>("Invalid date", HttpStatus.BAD_REQUEST);
        }
        workDetail.setLibrarian(librarian); 
        workDetail.setWorkDate(request.getWorkDate()); 
        workDetailRepo.save(workDetail);
        return new ResponseEntity<>("Update success", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteWorkDetail(int workDetailId) 
    {
        WorkDetail workDetail = workDetailRepo.findById(workDetailId).orElse(null);
        if(workDetail==null)
        {
            return new ResponseEntity<>("Delete fail", HttpStatus.BAD_REQUEST);
        } 
        workDetailRepo.delete(workDetail); 
        return new ResponseEntity<>("Delete success", HttpStatus.OK);
    }
    
}

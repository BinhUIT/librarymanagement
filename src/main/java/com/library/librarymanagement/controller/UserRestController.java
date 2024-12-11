package com.library.librarymanagement.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.request.LoginRequest;
import com.library.librarymanagement.request.RegisterRequest;
import com.library.librarymanagement.request.UpdateNormalInfoRequest;
import com.library.librarymanagement.request.UpdatePasswordRequest;
import com.library.librarymanagement.response.LoginResponse;
import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
@CrossOrigin
@RestController

public class UserRestController {  
    @Autowired
    private UserService userService;  
    
    @Autowired 
    private TokenSecurity tokenSecurity;
     
    @PostMapping("/login") 
    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest request) 
    { 
        return userService.handleLogin(request);
    }  
    @GetMapping("/verify")
    public String getMethodName(@Param("code") String code) { 
        if(userService.verify(code)) 
        return "library/VerifySuccess"; 
        else 
        return "library/VerifyFail";
        
    } 
    @PostMapping(value="/register", consumes = "application/json" ) 
    public ResponseEntity<String> userRegister(@RequestBody RegisterRequest request, HttpServletRequest http) throws MessagingException, UnsupportedEncodingException 
    { 
       System.out.println("Start");
        return userService.handleRegister(request, getSiteURL(http));
    } 
    private String getSiteURL(HttpServletRequest http) 
    { 
        String siteURL = http.getRequestURL().toString();
        return siteURL.replace(http.getServletPath(), "");
    } 
    @GetMapping("/testToken")  
    public ResponseEntity<String> test(@RequestHeader("Authorization") String authHeader) 
    { 
        return userService.testToken(authHeader);
    }   

    @PutMapping("/update/normal") 
    public ResponseEntity<String> updateNormalInfo(@RequestHeader("Authorization") String authHeader, @RequestBody UpdateNormalInfoRequest request) 
    { 
        if(authHeader==null||!authHeader.startsWith("Bearer ")) 
        { 
            return new ResponseEntity<>("Fail to send request", HttpStatus.BAD_REQUEST); 

        }
        String token = authHeader.substring(7); 
        return userService.changeNormalInfo(request, token);

    }  


    @PutMapping("/update/password") 
    public ResponseEntity<String> updatePassword(@RequestHeader("Authorization") String authHeader, @RequestBody UpdatePasswordRequest request) 
    { 
        if(authHeader==null||!authHeader.startsWith("Bearer ")) 
        { 
            return new ResponseEntity<>("Fail to send request", HttpStatus.BAD_REQUEST); 

        } 
        String token = authHeader.substring(7); 
        return userService.changePassword(request, token); 

    }   

    @PutMapping("/update/email")
    public ResponseEntity<String> updateEmail(@RequestHeader("Authorization") String authHeader, @RequestBody String newEmail, HttpServletRequest http) throws MessagingException, UnsupportedEncodingException
    { 
        if(authHeader==null||!authHeader.startsWith("Bearer ")) 
        { 
            return new ResponseEntity<>("Fail to send request", HttpStatus.BAD_REQUEST); 

        } 
        String token = authHeader.substring(7);  
        return userService.changeEmail(newEmail, token, getSiteURL(http));
    }

    @PutMapping("/forget_password") 
    public ResponseEntity<String> forgetPassword(@RequestBody String nameOrEmail) throws MessagingException, UnsupportedEncodingException
    {  
        return userService.forgetPassword(nameOrEmail);
    } 

    @GetMapping("/find/user/{id}") 
    public User findUserById(@PathVariable int id) 
    { 
        return userService.findUserById(id);
    }
    



}

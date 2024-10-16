package com.library.librarymanagement.controller;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.request.ChangePasswordRequest;
import com.library.librarymanagement.request.RegisterRequest;
import com.library.librarymanagement.request.UserChangeNameRequest;
import com.library.librarymanagement.request.UserLoginRequest;
import com.library.librarymanagement.request.UserUpdateEmailRequest;
import com.library.librarymanagement.request.UserUpdateNormalInfoRequest;
import com.library.librarymanagement.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest; 
@RestController
public class UserController { 
    @Autowired 
    private UserService userService; 
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request, HttpServletRequest http) throws  MessagingException, UnsupportedEncodingException 
    { 
        return userService.register(request, getSiteURL(http));
    } 
    @PostMapping("/login") 
    public String login(@RequestBody UserLoginRequest request) 
    { 
        return userService.login(request);
    } 
    private String getSiteURL(HttpServletRequest http) 
    { 
        String siteURL = http.getRequestURL().toString();
        return siteURL.replace(http.getServletPath(), "");
    } 
    @GetMapping("/verify")
    public String getMethodName(@Param("code") String code) { 
        if(userService.verify(code)) 
        return "Verify success"; 
        else 
        return "Verify fail";
        
    } 
     

    @PutMapping("/update/normalInfo")  
    public String updateNormalInfo(@RequestBody UserUpdateNormalInfoRequest request)
    { 
        return userService.changeNormalInfo(request); 
    } 

    @PutMapping("/update/email") 
    public String updateEmail(@RequestBody UserUpdateEmailRequest request, HttpServletRequest http) throws MessagingException, UnsupportedEncodingException
    { 
        return userService.changeEmail(request, getSiteURL(http)); 
    } 
    @PutMapping("/update/password") 
    public String updatePassword(@RequestBody ChangePasswordRequest request) 
    { 
        return userService.changePassword(request);
    }  

    @GetMapping("/forget_password/{id}") 
    public String forgetPassword(@PathVariable int id, HttpServletRequest http) throws MessagingException, UnsupportedEncodingException
    {
        return userService.forgetPassword(id, getSiteURL(http));
    }
     
    @PutMapping("/update/username") 
    public String changeUsername(@RequestBody UserChangeNameRequest request, @RequestHeader("Authorization") String authHeader) 
    {
        return userService.changeUserName(request, authHeader);
    }

}

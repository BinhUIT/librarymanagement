package com.library.librarymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.library.librarymanagement.security.TokenSecurity;
import com.library.librarymanagement.service.LibrarianService;

@RestController
public class LibrarianController { 
    @Autowired 
    private LibrarianService librarianService;

    @Autowired 
    private TokenSecurity tokenSecurity;  
    

}

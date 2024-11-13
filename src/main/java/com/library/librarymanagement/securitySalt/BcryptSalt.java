package com.library.librarymanagement.securitySalt;

import org.springframework.stereotype.Component;

@Component
public class BcryptSalt { 
    
     private String salt; 
     public BcryptSalt() 
     {
        salt =  "$2a$10$abcdefghijklmnopqrstuv"; 
     }  
     public String getSalt() 
     {
        return salt;
     }

}

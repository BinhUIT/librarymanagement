package com.library.librarymanagement.request;

public class LoginRequest { 
    private String nameOrEmail; 
    private String password; 
    public LoginRequest() 
    {

    } 
    public LoginRequest(String nameOrEmail, String password) 
    { 
        this.nameOrEmail=nameOrEmail; 
        this.password=password; 
    } 
    public String getNameOrEmail() 
    {
        return this.nameOrEmail;
    } 
    public String getPassword() 
    {
        return this.password;
    }

}

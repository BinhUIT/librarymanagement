package com.library.librarymanagement.request;

public class UserLoginRequest { 
    private String fullname; 
    private String password; 
    public UserLoginRequest() 
    {

    } 
    public UserLoginRequest(String fullname, String password) 
    {
        this.fullname=fullname; 
        this.password=password;
    }  
    public String getFullname() 
    {
        return this.fullname;
    }  
    public void setFullname(String fullname) {
        this.fullname=fullname;
    } 
    public String getPassword() 
    {
        return this.password;
    } 
    public void setPassword(String password) 
    {
        this.password=password;
    }

}

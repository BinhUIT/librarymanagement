package com.library.librarymanagement.request;

public class UserUpdateEmailRequest { 
    private int userId; 
    private String email; 
    public UserUpdateEmailRequest() 
    {} 
    public UserUpdateEmailRequest(int userId, String email) 
    { 
        this.userId=userId; 
        this.email=email;
    } 
    public int getUserId() 
    {
        return this.userId;
    } 
    public void setUserId(int userId) 
    { 
        this.userId=userId; 
    } 
    public String getEmail() 
    {
        return this.email; 
    } 
    public void setEmail(String email) 
    {  
        this.email=email;
    }
}

package com.library.librarymanagement.request;

public class UserChangeNameRequest { 
    private int userId; 
    private String fullName; 
    public UserChangeNameRequest() 
    {
        
    } 
    public UserChangeNameRequest(int userId, String fullName) 
    { 
        this.userId= userId; 
        this.fullName= fullName; 
    } 
    public int getUserId()  
    {
        return this.userId;
    } 
    public void setUserId(int userId) 
    {
        this.userId= userId;
    }  
    public String getFullName() 
    {
        return this.fullName; 
    } 
    public void setFullName(String fullName) 
    { 
        this.fullName= fullName;
    }
}

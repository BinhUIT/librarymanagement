package com.library.librarymanagement.request;

public class UserResetPasswordRequest { 
    private int userId; 
    private String newPassword;  
    public UserResetPasswordRequest() 
    {

    } 
    public UserResetPasswordRequest(int userId, String newPassword) 
    { 
        this.userId=userId; 
        this.newPassword = newPassword; 
    } 
    public int getUserId() 
    {
        return this.userId;
    } 
    public void setUserId(int userId) 
    {
        this.userId= userId;
    } 
    public String getNewPassword() 
    { 
        return this.newPassword;
    } 
    public void setNewPassword(String newPassword) 
    { 
        this.newPassword = newPassword;
    }
}

package com.library.librarymanagement.request;

public class ChangePasswordRequest { 
    private int userId; 
    private String oldPassword; 
    private String newPassword;  
    private String confirmPassword;
     
    public ChangePasswordRequest() 
    {

    } 
    public ChangePasswordRequest(int userId, String oldPassword, String newPassword, String confirmPassword) 
    { 
        this.userId= userId; 
        this.oldPassword = oldPassword; 
        this.newPassword = newPassword; 
        this.confirmPassword= confirmPassword; 
    } 
    public int getUserId() 
    {
        return this.userId;
    } 
    public void setUserId(int userId) 
    {
        this.userId= userId;
    } 
    public String getOldPassword() 
    {
        return this.oldPassword; 

    } 
    public void setOldPassword(String oldPassword) 
    {  
        this.oldPassword= oldPassword; 
    } 
    public String getNewPassword() 
    {
        return this.newPassword;
    } 
    public void setNewPassword(String newPassword) 
    { 
        this.newPassword = newPassword;
    } 
    public String getConfirmPassword() 
    {
        return this.confirmPassword;
    } 
    public void setConfirmPassword(String confirmPassword) 
    { 
        this.confirmPassword = confirmPassword; 
    }

}

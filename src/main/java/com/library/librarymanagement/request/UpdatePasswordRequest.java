package com.library.librarymanagement.request;

public class UpdatePasswordRequest { 
    private String oldPassword; 
    private String newPassword; 
    private String repeatNewPassword; 
    public UpdatePasswordRequest() {

    } 
    public UpdatePasswordRequest(String oldPassword, String newPassword, String repeatNewPassword) 
    { 
        this.oldPassword = oldPassword; 
        this.newPassword = newPassword; 
        this.repeatNewPassword = repeatNewPassword; 
    }  
    public String getOldPassword() 
    { 
        return this.oldPassword;
    }
    public String getNewPassword() 
    {
        return this.newPassword;
    }  
    public String getRepeatNewPassword() 
    {
        return this.repeatNewPassword;
    }

}

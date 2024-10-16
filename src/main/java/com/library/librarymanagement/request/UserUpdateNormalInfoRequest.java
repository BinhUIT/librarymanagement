package com.library.librarymanagement.request;

public class UserUpdateNormalInfoRequest { 
    private int userId;  
    private String address; 
    private String phoneNumber; 
    public UserUpdateNormalInfoRequest() 
    {

    } 
    public UserUpdateNormalInfoRequest(int userId, String address, String phoneNumber) 
    { 
        this.userId=userId; 
        this.address=address; 
        this.phoneNumber= phoneNumber;
    } 
    public int getUserId() 
    {
        return this.userId;
    } 
    public void setUserId(int userId) 
    {
        this.userId=userId;
    }  
    public String getAddress() 
    {
        return this.address;
    } 
    public void setAddress(String address) 
    {
        this.address=address;
    } 
    public String getPhoneNumber ()
    { 
        return this.phoneNumber;
    } 
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber= phoneNumber; 
    }
}

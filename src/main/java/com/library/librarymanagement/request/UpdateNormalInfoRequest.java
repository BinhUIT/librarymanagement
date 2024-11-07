package com.library.librarymanagement.request;

public class UpdateNormalInfoRequest {  
    private String fullname;
    private String address; 
    private String phoneNumber; 
    public UpdateNormalInfoRequest() 
    {

    } 
    public UpdateNormalInfoRequest(String fullname, String address, String phoneNumber) 
    {  
        this.fullname=fullname;
        this.address= address; 
        this.phoneNumber= phoneNumber;
    }   
    public String getFullname() 
    {
        return this.fullname;
    }
    public String getAddress() 
    {
        return this.address;
    } 
    public String getPhoneNumber() 
    {
        return this.phoneNumber;
    }
}

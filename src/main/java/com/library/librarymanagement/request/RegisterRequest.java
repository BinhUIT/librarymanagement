package com.library.librarymanagement.request;

public class RegisterRequest { 
    private String fullname; 
    private String address; 
    private String phoneNumber; 
    private String email; 
    private String password; 
    public RegisterRequest() {

    } 
    public RegisterRequest(String fullname, String address, String phoneNumber, String email, String password) 
    { 
        this.fullname=fullname; 
        this.address=address;  
        this.phoneNumber=phoneNumber; 
        this.email=email; 
        this.password=password;
    } 
    public String getFulllname() 
    {
        return this.fullname;
    } 
    public void setFullname(String fullname) 
    {  
        this.fullname=fullname;
    } 
    public String getAddress() {
        return this.address;
    } 
    public void setAddress(String address) 
    {
        this.address=address;
    } 
    public String getPhoneNumber()  
    {
        return this.phoneNumber;
    } 
    public void setPhoneNumber(String phoneNumber)     
    {  
        this.phoneNumber=phoneNumber;
    }
    public String getEmail() {
        return this.email;
    } 
    public void setEmail(String email) {
        this.email=email;
    } 
    public String getPassword() {
        return this.password;   
    } 
    public void setPassword(String password) { 
        this.password=password;
    }

}

package com.library.librarymanagement.request;

public class RegisterRequest { 
    private String fullName; 
    private String address;  
    private String phoneNumber; 
    private String email; 
    private String password; 
    private String repeatPassword; 

    public RegisterRequest() 
    {

    } 
    public RegisterRequest(String fullName, String address, String phoneNumber, String email, String password, String repeatPassword) 
    { 
        this.fullName=fullName; 
        this.address=address; 
        this.phoneNumber = phoneNumber; 
        this.email=email; 
        this.password=password; 
        this.repeatPassword = repeatPassword; 

    } 
    public String getFullName() 
    {
        return this.fullName;
    } 
    public String getAddress() 
    {
        return this.address;
    } 
    public String getPhoneNumber() 
    {
        return this.phoneNumber;
    } 
    public String getEmail() 
    {
        return this.email; 
    } 
    public String getPassword() 
    {
        return this.password; 

    } 
    public String getRepeatPassword() 
    {
        return this.repeatPassword;
    }

}

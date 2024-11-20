package com.library.librarymanagement.entity;
import com.library.librarymanagement.request.RegisterRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants; 
@Entity 
@FieldNameConstants 
@Table(name="USER")
public class User {  
    @Id
    @Column(name="USERID", columnDefinition="int not null", nullable=false) 
    private int userId;  

    @Column(name="FULLNAME", columnDefinition="varchar(45) not null", nullable=false) 
    private String fullName; 
    
    @Column(name="ADDRESS", columnDefinition="varchar(45) not null", nullable=false) 
    private String address; 

    @Column(name="PHONENUMBER", columnDefinition="varchar(45) not null", nullable=false) 
    private String phoneNumber; 

    @Column(name="EMAIL", columnDefinition="varchar(45) not null", nullable=false) 
    private String email; 

    @Column(name="ENABLE", columnDefinition="boolean not null", nullable=false) 
    private boolean enable; 

    @Column(name="PASSWORD", columnDefinition="varchar(100) not null", nullable=false) 
    private String password; 

    @Column(name="ROLE", columnDefinition="int not null", nullable=false)
    private int role; 

    @Column(name="VERIFICATIONCODE", columnDefinition="varchar(100) not null", nullable=false)
    private String verificationCode;

    public User() 
    {

    } 
    public User(int userId, String fullName, String address, String phoneNumber, String email, boolean enable, String password, int role, String verificationCode) { 
        this.userId= userId; 
        this.fullName=fullName;
        this.address=address;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.enable=enable;
        this.password=password;
        this.role=role;
        this.verificationCode=verificationCode;
    }  
    public User(RegisterRequest request) 
    {
        this.fullName=request.getFullName(); 
        this.address=request.getAddress(); 
        this.phoneNumber = request.getPhoneNumber(); 
        this.email= request.getEmail(); 
        this.role=request.getRole();
        this.enable=false; 
        this.verificationCode="";
    }
    public int getUserId() 
    {
        return this.userId; 
    } 
    public void setUserId(int userId) {
        this.userId=userId;
    } 
    public String getFullname() 
    {
        return this.fullName;
    }  
    public void setFullname(String fullName) 
    {
        this.fullName=fullName;
    } 
    public String getAddress() 
    {
        return this.address;
    } 
    public void setAddress(String address) 
    {
        this.address=address;
    }
    public String getEmail() 
    { 
        return this.email;
    }  
    public void setEmail(String email) 
    {
        this.email=email;
    } 
    public boolean getEnable() {
        return this.enable;
    } 
    public void setEnable(boolean enable) 
    {
        this.enable=enable;
    }  
    public String getPassword() 
    {
        return this.password;
    } 
    public void setPassword(String password) 
    {
        this.password=password;
    } 
    public int getRole() 
    {
        return this.role;
    } 
    public void setRole(int role) 
    {
        this.role=role; 
    } 
    public String getVerificationCode() 
    {
        return this.verificationCode;
    } 
    public void setVerificationCode(String verificationCode) 
    {
        this.verificationCode=verificationCode;
    } 
    public String getPhoneNumber() 
    { 
        return this.phoneNumber;
    } 
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber=phoneNumber;
    }



}

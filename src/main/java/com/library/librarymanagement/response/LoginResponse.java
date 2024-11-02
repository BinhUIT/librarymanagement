package com.library.librarymanagement.response;

public class LoginResponse { 
    private int userId; 
    private String token; 
    private int role; 
    public LoginResponse() 
    {

    } 
    public LoginResponse(int userId, String token, int role) 
    {
        this.userId=userId; 
        this.token=token; 
        this.role=role;
    } 
    public int getUserId() 
    {
        return this.userId;
    } 
    public String getToken() 
    {
        return this.token;
    } 
    public int getRole() 
    {
        return this.role;
    }

}

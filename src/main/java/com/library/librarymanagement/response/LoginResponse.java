package com.library.librarymanagement.response;

public class LoginResponse { 
    private int userId; 
    private String token; 
    public LoginResponse() 
    {

    } 
    public LoginResponse(int userId, String token) 
    {
        this.userId=userId; 
        this.token=token;
    } 
    public int getUserId() 
    {
        return this.userId;
    } 
    public String getToken() 
    {
        return this.token;
    }

}

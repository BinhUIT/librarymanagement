package com.library.librarymanagement.response;

import java.util.List;

public class BorrowResponse { 
    private String message;
    private List<Object> listRespone;  

    public BorrowResponse() 
    {

    } 
    public BorrowResponse(String message, List<Object> listResponse) 
    {
        this.message= message;
        this.listRespone= listResponse;
    } 

    public String getMessage() 
    {
        return this.message;
    } 
    public List<Object> getListResponse()
    {
        return this.listRespone;
    } 
    public void setMessage(String message) 
    {
        this.message= message;
    } 
    public void addResponse(Object response) 
    {
        this.listRespone.add(response);
    }
}

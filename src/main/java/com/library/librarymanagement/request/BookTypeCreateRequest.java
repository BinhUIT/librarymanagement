package com.library.librarymanagement.request;

public class BookTypeCreateRequest { 
    private String name; 
    public BookTypeCreateRequest() 
    {

    } 
    public BookTypeCreateRequest(String name) 
    { 
        this.name=name;
    } 
    public String getName() 
    {
        return this.name;
    }

}

package com.library.librarymanagement.request;

public class BookTitleCreateRequest { 
    private String name; 
    private int bookTypeId; 
    private String author;

    public BookTitleCreateRequest() 
    { 

    } 
    public BookTitleCreateRequest(String name, int bookTypeId, String author) 
    { 
        this.name=name; 
        this.bookTypeId = bookTypeId; 
        this.author=author;
    } 

    public String getName() 
    {
        return this.name;
    } 
    public int getBookTypeId() 
    {
        return this.bookTypeId;
    } 
    public String getAuthor() 
    { 
        return this.author;
    }

}

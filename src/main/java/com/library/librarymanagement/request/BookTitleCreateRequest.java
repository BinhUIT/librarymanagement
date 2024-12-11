package com.library.librarymanagement.request;

public class BookTitleCreateRequest { 
    private String name; 
    private int bookTypeId; 
    private String author;
    private String nxb;
    private int year;
    private String language;
    private int pageAmount;
    private String review;

    public BookTitleCreateRequest() 
    { 

    } 
    public BookTitleCreateRequest(String name, int bookTypeId, String author, String nxb, int year, String language, int pageAmount, String review) 
    { 
        this.name=name; 
        this.bookTypeId = bookTypeId; 
        this.author=author; 
        this.nxb=nxb;
        this.year=year;
        this.language=language;
        this.pageAmount=pageAmount;
        this.review=review;
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
    public String getNxb() 
    {
        return this.nxb;
    } 
    public int getYear() 
    {
        return this.year;
    } 
    public String getLanguage() 
    {
        return this.language;
    } 
    public int getPageAmount() 
    {
        return this.pageAmount;
    } 
    public String getReview() 
    {
        return this.review;
    }
}

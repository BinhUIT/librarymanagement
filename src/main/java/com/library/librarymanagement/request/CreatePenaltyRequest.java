package com.library.librarymanagement.request;

public class CreatePenaltyRequest {
    private int readerId;
    private int money;
    private String content;

    public CreatePenaltyRequest() {

    } 
    public CreatePenaltyRequest(int readerId, int money, String content) 
    {
        this.readerId= readerId;
        this.money= money;
        this.content= content;
    } 

    public int getReaderId() 
    {
        return this.readerId;
    } 
    public int getMoney() 
    {
        return this.money;
    } 
    public String getContent() 
    {
        return this.content;
    }
}

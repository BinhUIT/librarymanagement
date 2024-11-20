package com.library.librarymanagement.request;

public class ReturningDetailRequest { 
    private int borrowingCardDetailId; 
    public ReturningDetailRequest() 
    {

    } 
    public ReturningDetailRequest(int borrowingCardDetailId) 
    { 
        this.borrowingCardDetailId= borrowingCardDetailId;
    } 
    public int getBorrowingCardDetailId() 
    {
        return this.borrowingCardDetailId;
    }

}

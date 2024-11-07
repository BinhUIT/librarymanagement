package com.library.librarymanagement.request;

import com.library.librarymanagement.entity.BorrowingCardDetail;

public class BorrowingDetailRequest { 
    private int bookTitleId; 
    private int amount; 
    public BorrowingDetailRequest() 
    {

    } 
    public BorrowingDetailRequest(int bookTitleId, int amount) 
    { 
        this.bookTitleId=bookTitleId; 
        this.amount=amount;
    } 

    public int getBookTitleId() 
    {
        return this.bookTitleId; 

    } 
    public int getAmount() 
    { 
        return this.amount;
    }

}

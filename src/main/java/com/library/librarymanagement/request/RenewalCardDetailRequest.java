package com.library.librarymanagement.request;

import com.library.librarymanagement.entity.RenewalCardDetail;

public class RenewalCardDetailRequest { 
    private int borrowingCardDetailId; 
    public RenewalCardDetailRequest() 
    {

    } 
    public RenewalCardDetailRequest(int borrowingCardDetailId) 
    {
        this.borrowingCardDetailId=borrowingCardDetailId;
    } 
    public int getBorrowingCardDetailId() 
    {
        return this.borrowingCardDetailId;
    }

}

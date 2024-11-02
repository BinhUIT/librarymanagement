package com.library.librarymanagement.request;

import java.util.Date;

public class BorrowingRequest { 
    private int readerId;
    private Date implementDate; 
    private int requestTypeId; 

    public BorrowingRequest() 
    { 

    } 
    public BorrowingRequest(int readerId, Date implementDate, int requestTypeId) 
    { 
        this.readerId=readerId;
        this.implementDate=implementDate; 
        this.requestTypeId=requestTypeId;
    } 

    public int getReaderId() 
    {
        return this.readerId;
    } 
    public Date getImplementDate() 
    {
        return this.implementDate;
    } 
    public int getRequestTypeId() 
    {
        return this.requestTypeId;
    }


}

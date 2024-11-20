package com.library.librarymanagement.request;

import java.util.Date;
import java.util.List;

public class BuyBookBillRequest { 
    private int librarianId;
    private Date implementDate; 
    private List<BuyBookBillDetailRequest> listDetailRequest;

    public BuyBookBillRequest() 
    {

    } 
    public BuyBookBillRequest(int librarianId, Date implementDate, List<BuyBookBillDetailRequest> listDetailRequest) 
    { 
        this.librarianId = librarianId; 
        this.implementDate= implementDate; 
        this.listDetailRequest = listDetailRequest;
    } 

    public int getLibrarianId() 
    { 
        return this.librarianId;
    } 
    public Date getImplementDate () 
    {
        return this.implementDate;
    } 
    public List<BuyBookBillDetailRequest> getListDetailRequest() 
    { 
        return this.listDetailRequest;
    }

}

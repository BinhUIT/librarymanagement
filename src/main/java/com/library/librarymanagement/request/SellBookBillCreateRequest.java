package com.library.librarymanagement.request;

import java.util.Date;
import java.util.List;

public class SellBookBillCreateRequest { 
    private Date implementDate; 
    List<SellBookBillDetailRequest> listDetailRequest; 
    public SellBookBillCreateRequest() 
    { 

    } 
    public SellBookBillCreateRequest(Date implementDate,List<SellBookBillDetailRequest> listDetailRequest ) 
    { 
        this.implementDate=implementDate; 
        this.listDetailRequest=listDetailRequest;
    } 

    public Date getDate() 
    { 
        return this.implementDate;
    }  
    public List<SellBookBillDetailRequest> getListDetailRequest() 
    { 
        return this.listDetailRequest;
    }
}

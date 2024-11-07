package com.library.librarymanagement.request;

import java.util.Date;

public class ServiceRequest { 
    private int readerId; 
    private Date implementDate; 
    private int serviceTypeId; 

    public ServiceRequest() 
    { 

    } 
    public ServiceRequest(int readerId, Date implementDate, int serviceTypeId) 
    { 
        this.readerId=readerId; 
        this.implementDate=implementDate; 
        this.serviceTypeId=serviceTypeId;
    } 

    public int getReaderId() 
    {
        return this.readerId;
    } 
    public Date getImplementDate() 
    { 
        return this.implementDate;
    } 
    public int getServiceTypeId() 
    {
        return this.serviceTypeId;
    }

}

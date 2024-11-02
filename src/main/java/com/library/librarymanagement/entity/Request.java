package com.library.librarymanagement.entity;

import java.util.Date;

import com.library.librarymanagement.request.BorrowingRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants;

@Entity 
@FieldNameConstants 
@Table(name="REQUEST")
public class Request { 
    @Id
    @Column(name="REQUESTID", columnDefinition="INT NOT NULL") 
    private int requestId; 

    @Column(name="READERID", columnDefinition="INT") 
    private int readerId;

    @Column(name="LIBRARIANID", columnDefinition="INT") 
    private int librarianId; 

    @Column(name="IMPLEMENTDATE", columnDefinition="DATE") 
    private Date implementDate;

    @Column(name="ISACCEPT", columnDefinition="BOOLEAN") 
    private boolean isAccept; 

    @Column(name="REQUESTTYPEID", columnDefinition="INT") 
    private int requestTypeId;

    public Request() 
    {

    } 

    public Request(int requestId, int readerId, int librarianId, Date implementDate, boolean isAccept, int requestTypeId) 
    { 
        this.requestId=requestId;
        this.readerId=readerId;
        this.librarianId=librarianId; 
        this.implementDate=implementDate; 
        this.isAccept=isAccept; 
        this.requestTypeId=requestTypeId;
    }  
    
    public Request(BorrowingRequest request) 
    {
        this.readerId=request.getReaderId();
        this.librarianId=-1;
        this.implementDate=request.getImplementDate();
        this.isAccept=false;
        this.requestTypeId=request.getRequestTypeId();
    }

    public int getRequestId() 
    {
        return this.requestId;
    } 
    public void setRequestId(int requestId) 
    {
        this.requestId=requestId;
    } 
    public int getReaderId() 
    {
        return this.readerId;
    } 
    public void setReaderId(int readerId) 
    { 
        this.readerId=readerId;
    } 

    public int getLibrarianId() {
        return this.librarianId;
    } 
    public void setLibrarianId(int librarianId) 
    {
        this.librarianId=librarianId;
    } 

    public Date implementDate() 
    {
        return this.implementDate;
    }  
    public void setImplementDate(Date implementDate) 
    { 
        this.implementDate=implementDate;
    } 

    public boolean getIsAccept() 
    { 
        return this.isAccept;
    } 
    public void setIsAccept(boolean isAccept) 
    {
        this.isAccept=isAccept;
    }

    public int getRequestTypeId() 
    { 
        return this.requestTypeId;
    } 
    public void setRequestTypeId(int requestTypeId) 
    {
        this.requestTypeId= requestTypeId;
    }
}

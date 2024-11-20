package com.library.librarymanagement.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants;

@Entity 
@FieldNameConstants
@Table(name="BORROWINGCARDDETAIL")
public class BorrowingCardDetail {  
    @Id 
    private int id;
    @ManyToOne 
    @JoinColumn(name="SERVICEID", columnDefinition="INT", referencedColumnName="SERVICEID") 
    private Service service; 

    @ManyToOne
    @JoinColumn(name="BOOKID", columnDefinition="INT", referencedColumnName="ID") 
    private BookImagePath book;  

    @Column(name="EXPIREDATE", columnDefinition="DATE") 
    private Date expireDate;

    public BorrowingCardDetail() 
    {

    }  
    public BorrowingCardDetail(int id,Service service, BookImagePath book, Date expireDate) 
    {  

        this.id=id;
        this.service=service; 
        this.book=book; 
        this.expireDate= expireDate;
    }  
    public int getId() 
    { 
        return this.id;
    }
    public Service getService() 
    {
        return this.service;
    } 
    public BookImagePath getBook() 
    {
        return this.book; 
    }
    public void setService(Service service) 
    { 
        this.service=service;
    } 
    public void setBook(BookImagePath book) 
    { 
        this.book=book;
    } 
    public Date getExpireDate() 
    {
        return this.expireDate;
    } 
    public void setExpireDate(Date expireDate) 
    { 
        this.expireDate = expireDate;
    }

}

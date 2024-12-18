package com.library.librarymanagement.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(name = "Status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    public enum Status {
        PENDING,
        CANCELLED,
        BORROWING,
        RETURNED,
    }

    public BorrowingCardDetail() 
    {

    }

    public BorrowingCardDetail(int id, Service service, BookImagePath book, java.util.Date expireDate) {
        this.id=id;
        this.service=service; 
        this.book = book;

        if (expireDate != null) {
            this.expireDate = new Date(expireDate.getTime());
        } else {
            this.expireDate = null;
        }
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
        if (this.expireDate != null) {
            return new Date(this.expireDate.getTime());
        } else {
            return null;
        }
    }

    public void setExpireDate(java.util.Date expireDate) 
    { 
        if (expireDate != null) {
            this.expireDate = new Date(expireDate.getTime());
        } else {
            this.expireDate = null;
        }
    }

    public Status getStatus() {
        return status;
    }

    public boolean updateStatus(final Status newStatus) {
        boolean isValidValue = switch (newStatus) {
            case BORROWING, CANCELLED -> this.status == Status.PENDING;
            case RETURNED -> this.status == Status.BORROWING;
            default -> false;
        };

        if (isValidValue) {
            this.status = newStatus;
        }

        return isValidValue;
    }
}

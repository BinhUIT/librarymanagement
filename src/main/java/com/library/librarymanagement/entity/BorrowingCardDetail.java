package com.library.librarymanagement.entity;

import java.sql.Date;
import java.util.Calendar;

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
        RENEWAL,
        DESTROY
    }

    public BorrowingCardDetail() 
    {

    }

    public BorrowingCardDetail(int id, Service service, BookImagePath book, java.util.Date expireDate) {
        this.id=id;
        this.service=service; 
        this.book = book;
        this.status= Status.PENDING;
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
        boolean isValidValue; 
        System.out.println(this.status); 
        System.out.println(this.id);
       switch (newStatus) {
            case Status.BORROWING : isValidValue=(this.status == Status.PENDING||this.status==Status.RENEWAL);
                break;
            case Status.CANCELLED: isValidValue=(this.status!=Status.RETURNED);
            break;
            case Status.RETURNED :isValidValue=( this.status ==  Status.BORROWING||this.status==Status.RENEWAL);
            break; 
            case Status.RENEWAL: isValidValue= (this.status==Status.BORROWING);
            break;
            case Status.DESTROY: isValidValue=true;
            
            default: isValidValue= false;
        };
        System.out.println(isValidValue);
        if (isValidValue) {
            this.status = newStatus; 
            if(this.status==Status.RENEWAL) 
            {
                java.util.Date utilDate= new java.util.Date(this.expireDate.getTime()); 
                Calendar calendar = Calendar.getInstance(); 
                calendar.setTime(utilDate); 
                calendar.add(Calendar.DAY_OF_MONTH, 7);
                this.expireDate= new Date(calendar.getTimeInMillis());
                
            }
        }

        return isValidValue;
    }
}

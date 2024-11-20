package com.library.librarymanagement.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants;

@Entity 
@FieldNameConstants
@Table(name="BUYBOOKBILL")
public class BuyBookBill {  
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    @Column(name="BUYBOOKBILLID", columnDefinition="INT") 
    private int buyBookBillId;

    @ManyToOne 
    @JoinColumn(name="LIBRARIANID", columnDefinition="INT", referencedColumnName="USERID") 
    private User librarian; 

    @Column(name="IMPLEMENTDATE", columnDefinition="DATE") 
    private Date implementDate; 


    public BuyBookBill() 
    {

    } 
    public BuyBookBill(int buyBookBillId, User librarian, Date implementDate) 
    { 
        this.buyBookBillId= buyBookBillId; 
        this.librarian=librarian; 
        this.implementDate = implementDate;
    } 

    public int getBuyBookBillId() 
    { 
        return this.buyBookBillId;
    }  
    public User getLibrarianId() 
    { 
        return this.librarian;
    } 
    public Date getImplementDate() 
    { 
        return this.implementDate;
    } 

    


}

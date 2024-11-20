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
@Table(name="SELLBOOKBILL") 
public class SellBookBill {
    @Id 
    @Column(name="SELLBOOKBILLID", columnDefinition="INT") 
    private int sellBookBillId; 

    @Column(name="IMPLEMENTDATE", columnDefinition="DATE") 
    private Date implementDate;  

    @ManyToOne
    @JoinColumn(name="LIBRARIANID", columnDefinition="INT", referencedColumnName="USERID") 
    private User librarian;

    public SellBookBill() 
    { 

    } 

    public SellBookBill(int sellBookBillId, Date implementDate, User librarian) 
    { 
        this.sellBookBillId= sellBookBillId; 
        this.implementDate=implementDate; 
        this.librarian=librarian;
    } 

    public int getSellBookBillId() 
    { 
        return this.sellBookBillId; 
    } 

    public Date getImplementDate() 
    { 
        return this.implementDate;
    } 
    public User getLibrarian() 
    { 
        return this.librarian;
    }
}

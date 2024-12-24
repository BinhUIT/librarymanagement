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
@Table(name="RENEWALDETAIL")
public class RenewalDetail { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name="ID", columnDefinition = "INT") 
    private int id; 

    @ManyToOne 
    @JoinColumn(name="BORROWINGCARDDETAILID", columnDefinition = "INT", referencedColumnName = "ID") 
    private BorrowingCardDetail borrowingCardDetail;

    @Column(name="NEWEXPIREDATE", columnDefinition = "DATE") 
    private Date newExpireDate;

    @Column(name="STATUS", columnDefinition = "INT") 
    private int status;

    public RenewalDetail() 
    {

    } 
    public RenewalDetail(int id, BorrowingCardDetail borrowingCardDetail, Date newExpireDate) 
    {
        this.id=id;
        this.borrowingCardDetail= borrowingCardDetail;
        this.newExpireDate= newExpireDate;
        this.status= -1;
    } 

    public RenewalDetail(BorrowingCardDetail borrowingCardDetail, Date newExpireDate) 
    {
        this.borrowingCardDetail= borrowingCardDetail;
        this.newExpireDate= newExpireDate;
        this.status= -1;
    }

    public int getId() 
    {
        return this.id;
    } 
    public BorrowingCardDetail getBorrowingCardDetail() 
    {
        return this.borrowingCardDetail;
    } 
    public Date getNewExpireDate() 
    {
        return  this.newExpireDate;
    } 
    public int getStatus() 
    {
        return this.status;
    } 

    public void setStatus(int status) 
    {
        this.status= status;
    }
    
}

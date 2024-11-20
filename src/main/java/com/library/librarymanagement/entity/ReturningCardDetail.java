package com.library.librarymanagement.entity;

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
@Table(name="RETURNINGCARDDETAIL")
public class ReturningCardDetail { 
    @Id 
    @Column(name="ID", columnDefinition="INT") 
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private int id; 

    @ManyToOne 
    @JoinColumn(name="SERVICEID", columnDefinition="INT", referencedColumnName="SERVICEID") 
    private Service service; 

    @ManyToOne 
    @JoinColumn(name="BORROWINGCARDDETAILID", columnDefinition="INt", referencedColumnName="ID") 
    private BorrowingCardDetail borrowingCardDetail; 

    public ReturningCardDetail() 
    {

    } 
    public ReturningCardDetail(int id, Service service, BorrowingCardDetail borrowingCardDetail) 
    { 
        this.id=id; 
        this.service=service; 
        this.borrowingCardDetail=borrowingCardDetail; 
    } 

    public int getId() 
    { 
        return this.id;
    } 
    public Service getService() 
    {
        return this.service;
    } 
    public BorrowingCardDetail getBorrowingCardDetail() 
    {
        return this.borrowingCardDetail;
    }

}

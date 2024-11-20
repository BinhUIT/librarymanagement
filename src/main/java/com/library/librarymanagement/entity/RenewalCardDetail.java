package com.library.librarymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name="RENEWALCARDDETAIL")
public class RenewalCardDetail { 
    @Id 
    @Column(name="ID", columnDefinition="INT") 
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private int id; 

    @ManyToOne 
    @JoinColumn(name="SERVICEID", columnDefinition="INT", referencedColumnName="SERVICEID") 
    private Service service;  

     

    @Column(name="STATE", columnDefinition="BOOLEAN") 
    private boolean  state; 

    @ManyToOne 
    @JoinColumn(name="LIBRARIANID", columnDefinition="ÏNT", referencedColumnName="USERID") 
    private User librarian; 

    @ManyToOne 
    @JoinColumn(name="BORROWINGCARDDETAILID", columnDefinition="INT", referencedColumnName="ID") 
    private BorrowingCardDetail borrowingCardDetail;

    public RenewalCardDetail() 
    {

    } 
    public RenewalCardDetail(int id, Service service,  User librarian, BorrowingCardDetail borrowingCardDetail) 
    { 
        this.id=id; 
        this.service=service; 
        this.borrowingCardDetail=borrowingCardDetail;
        this.state=false; 
        this.librarian=librarian;
    } 

    public int getId() 
    { 
        return this.id;
    } 
    public Service getService() 
    {
        return this.service;
    } 
   
    public boolean getState() 
    { 
        return this.state;
    } 
    public void setState(boolean state) 
    { 
        if(librarian.getUserId()!=-1)  return; 
        this.state= state;
    } 

    public User getLibrarian() 
    {
        return this.librarian;
    } 

    public void setLibrarian(User librarian) 
    { 
        if(librarian.getUserId()!=-1) return; 
        this.librarian=librarian;
    } 
    public BorrowingCardDetail getBorrowingCardDetail() 
    {
        return this.borrowingCardDetail;
    }



}

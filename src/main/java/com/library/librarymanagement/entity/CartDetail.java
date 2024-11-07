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
@Table(name="CARTDETAIL") 
public class CartDetail {  
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    @Column(name="ID", columnDefinition="INT") 
    private int id; 

    
    @ManyToOne 
    @JoinColumn(name="USERID", columnDefinition="INT",referencedColumnName="USERID") 
    private User user; 

    @ManyToOne 
    @JoinColumn(name="BOOKTITLEID", columnDefinition="INT", referencedColumnName="ID") 
    private BookTitleImagePath bookTitle; 

    @Column(name="AMOUNT", columnDefinition="INT") 
    private int amount; 

    public CartDetail() 
    {

    } 
    public CartDetail(int id, User user, BookTitleImagePath bookTitle, int amount) 
    {
        this.id=id; 
        this.user=user; 
        this.bookTitle= bookTitle; 
        this.amount=amount; 
    } 

    public int getId() 
    { 
        return this.id; 
    } 
    public User getUser() 
    {
        return this.user;
    } 
    public BookTitleImagePath getBookTitleImagePath() 
    {
        return this.bookTitle;
    } 
    public int getAmount() 
    {
        return this.amount;
    } 

    public void setAmount(int amount) 
    { 
        this.amount=amount;
    }
    
}

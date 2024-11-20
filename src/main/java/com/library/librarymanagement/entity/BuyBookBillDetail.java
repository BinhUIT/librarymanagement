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
@Table(name="BUYBOOKBILLDETAIL")
public class BuyBookBillDetail { 
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    @Column(name="ID", columnDefinition="INT") 
    private int id; 
    
    @ManyToOne 
    @JoinColumn(name="BUYBOOKBILLID", columnDefinition="INT", referencedColumnName="BUYBOOKBILLID") 
    private BuyBookBill buyBookBill; 

    @ManyToOne 
    @JoinColumn(name="BOOKTITLEID", columnDefinition="INT", referencedColumnName="ID") 
    private BookTitleImagePath bookTitle; 

    @Column(name="AMOUNT", columnDefinition = "INT") 
    private int amount;

    @Column(name="PRICE", columnDefinition="PRICE") 
    private int price; 

    public BuyBookBillDetail() 
    { 

    } 
    public BuyBookBillDetail(int id, BuyBookBill buyBookBill, BookTitleImagePath bookTitle, int amount, int price) 
    { 
        this.id=id; 
        this.buyBookBill=buyBookBill; 
        this.bookTitle=bookTitle; 
        this.amount=amount; 
        this.price=price;
    } 


    public int getId() 
    { 
        return this.id;
    } 
    public BuyBookBill getBuyBookBill() 
    { 
        return this.buyBookBill;
    } 
    public BookTitleImagePath getBookTitle() 
    {
        return this.bookTitle; 
    } 
    public int getAmount() 
    {
        return this.amount;
    } 
    public int getPrice() 
    {
        return this.price;
    } 

    public void setBuyBookBill (BuyBookBill buyBookBill) { 
        this.buyBookBill= buyBookBill;
    }

}

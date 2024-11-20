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
@Table(name="SELLBOOKBILLDETAIL")
public class SellBookBillDetail { 
    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)  
    @Column(name="ID", columnDefinition="INT")
    private int id; 

    @ManyToOne
    @JoinColumn(name="SELLBOOKBILLID", columnDefinition="INT", referencedColumnName="SELLBOOKBILLID") 
    private SellBookBill sellBookBill;

    @ManyToOne 
    @JoinColumn(name="BOOKID", columnDefinition="INT", referencedColumnName="ID") 
    private BookImagePath book; 

    @Column(name="PRICE", columnDefinition="INT") 
    private int price; 

    public SellBookBillDetail() 
    { 

    } 
    public SellBookBillDetail(int id, SellBookBill sellBookBill, BookImagePath book, int price) 
    { 
        this.id=id; 
        this.sellBookBill= sellBookBill; 
        this.book=book; 
        this.price=price;
    } 

    public int getId() 
    { 
        return this.id;
    } 
    public SellBookBill getSellBookBill() 
    {
        return this.sellBookBill;
    } 
    public BookImagePath getBook() 
    { 
        return this.book;
    } 
    public int getPrice() 
    { 
        return this.price;
    }

}

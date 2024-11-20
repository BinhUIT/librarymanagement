package com.library.librarymanagement.request;

public class BuyBookBillDetailRequest { 
    private int bookTitleId; 
    private int amount; 
    private int price; 
     
    public BuyBookBillDetailRequest() 
    {

    } 
    public BuyBookBillDetailRequest(int bookTitleId, int amount, int price) 
    { 
        this.bookTitleId= bookTitleId; 
        this.amount=amount; 
        this.price=price;
    } 

    public int getBookTitleId() 
    {
        return this.bookTitleId; 
    } 
    public int getAmount() 
    {
        return this.amount;
    } 
    public int getPrice() 
    { 
        return this.price;
    }
}

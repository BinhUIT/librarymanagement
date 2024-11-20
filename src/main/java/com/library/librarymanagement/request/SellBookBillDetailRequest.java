package com.library.librarymanagement.request;

public class SellBookBillDetailRequest { 
     private int bookId; 
     private int price; 
     public SellBookBillDetailRequest()
     {

     } 

     public SellBookBillDetailRequest(int bookId, int price) 
     {  
        this.bookId= bookId; 
        this.price= price;
     }

     public int getBookId() 
     {
        return this.bookId;
     } 
     public int getPrice() 
     {  
        return this.price;
     }

}

package com.library.librarymanagement.request;

public class AddCartDetailRequest { 
    private int userId; 
    private int bookTitleId;
    private int amount; 
    public AddCartDetailRequest() {
    }
    public AddCartDetailRequest(int userId, int bookTitleId, int amount) 
    { 
        this.userId=userId; 
        this.bookTitleId= bookTitleId; 
        this.amount=amount;
    } 

    public int getUserId() 
    { 
        return this.userId; 

    } 
    public int getBookTitleId()
    { 
        return this.bookTitleId;
    }  
    public int getAmount() 
    { 
        return this.amount;
    }

}

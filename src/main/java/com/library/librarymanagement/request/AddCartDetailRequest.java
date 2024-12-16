package com.library.librarymanagement.request;

public class AddCartDetailRequest { 
     
    private int bookTitleId;
     
    public AddCartDetailRequest() {
    }
    public AddCartDetailRequest( int bookTitleId) 
    { 
         
        this.bookTitleId= bookTitleId; 
        
    } 

   
    public int getBookTitleId()
    { 
        return this.bookTitleId;
    }  
    

}

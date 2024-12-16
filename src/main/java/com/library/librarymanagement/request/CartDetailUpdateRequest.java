package com.library.librarymanagement.request;

public class CartDetailUpdateRequest {

    private int cartDetailId;
     
    private int amount; 
    public CartDetailUpdateRequest() 
    {

    } 
    public CartDetailUpdateRequest(int cartDetailId, int amount)
    { 
        this.cartDetailId= cartDetailId; 
         
        
        this.amount=amount;
    } 

    public int getCartDetailId() 
    {
        return this.cartDetailId;
    } 
    
     
    public int getAmount() 
    { 
        return this.amount;
    }
}

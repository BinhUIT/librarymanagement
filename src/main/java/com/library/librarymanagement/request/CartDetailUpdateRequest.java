package com.library.librarymanagement.request;

public class CartDetailUpdateRequest {

    private int cartDetailId;
    private int userId; 
    
    private int amount; 
    public CartDetailUpdateRequest() 
    {

    } 
    public CartDetailUpdateRequest(int cartDetailId, int userId, int amount)
    { 
        this.cartDetailId= cartDetailId; 
        this.userId= userId; 
        
        this.amount=amount;
    } 

    public int getCartDetailId() 
    {
        return this.cartDetailId;
    } 
    public int getUserId() 
    { 
        return this.userId;
    } 
     
    public int getAmount() 
    { 
        return this.amount;
    }
}

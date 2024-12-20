package com.library.librarymanagement.request;

import static org.mockito.ArgumentMatchers.*;

public class BorrowingDetailRequest { 
   private int cartDetailId;
   private int amount;

   public BorrowingDetailRequest() 
   {

   } 
   public BorrowingDetailRequest(int cartDetailId, int amount) 
   {
        this.cartDetailId= cartDetailId;
        this.amount= amount;
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

package com.library.librarymanagement.request;
 
import java.util.List;

public class BorrowingRequest { 
   private List<BorrowingDetailRequest> listRequest; 
   private BorrowingRequest() 
   {

   } 
   public BorrowingRequest(List<BorrowingDetailRequest> listRequest) 
   {
        this.listRequest= listRequest;
   }  
   public List<BorrowingDetailRequest> getListRequest() 
   {
        return this.listRequest;
   }

}

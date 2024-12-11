package com.library.librarymanagement.response;

import java.util.Date;

import com.library.librarymanagement.entity.BorrowingCardDetail;
import com.library.librarymanagement.entity.Service;

public class BookBorrowingDetailResponse { 
   private int id;
   private BookResponse bookResponse; 
   private Service service; 
   private Date expireDate;
   public BookBorrowingDetailResponse() 
   {

   } 
   public BookBorrowingDetailResponse(int id, BookResponse bookResponse, Service service, Date expireDate) 
   {
        this.id= id;
        this.bookResponse= bookResponse;
        this.service=service; 
        this.expireDate=expireDate;
   } 
   public BookBorrowingDetailResponse(BorrowingCardDetail borrowingCardDetail) 
   {
    this.id=borrowingCardDetail.getId();
    this.bookResponse= new BookResponse(borrowingCardDetail.getBook());
    this.service= borrowingCardDetail.getService(); 
    this.expireDate= borrowingCardDetail.getExpireDate();
   } 
   public int getId() 
   {
    return this.id;
   } 
   public BookResponse getBookResponse() 
   {
    return this.bookResponse;
   } 
   public Service getService() 
   {
    return this.service;
   } 
   public Date getExpireDate() 
   {
    return this.expireDate;
   }


}

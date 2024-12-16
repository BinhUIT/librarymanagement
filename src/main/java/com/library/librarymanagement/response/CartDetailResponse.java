package com.library.librarymanagement.response;

import com.library.librarymanagement.entity.BookTitleImageData;
import com.library.librarymanagement.entity.CartDetail;

public class CartDetailResponse
{
    private int id;
    private String title;
    private byte[] image;

    public CartDetailResponse() 
    {

    } 
    public CartDetailResponse(int id, String title, byte[] image) 
    {
        this.id=id;
        this.title=title;
        this.image=image;
    } 
    public CartDetailResponse(CartDetail cartDetail) 
    {
        this.id=cartDetail.getBookTitleImagePath().getId();
        this.title= cartDetail.getBookTitleImagePath().getName();
        BookTitleImageData bookTitleImageData= new BookTitleImageData(cartDetail.getBookTitleImagePath());
        this.image=bookTitleImageData.getImageData();
    }
    public int getId() 
    {
        return this.id;
    } 
    public String getTitle() 
    {
        return this.title;
    } 
    public byte[] getImage() 
    {
        return this.image;
    }
    
}
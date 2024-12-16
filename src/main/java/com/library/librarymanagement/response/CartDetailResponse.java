package com.library.librarymanagement.response;

import com.library.librarymanagement.entity.BookTitleImageData;
import com.library.librarymanagement.entity.CartDetail;

public class CartDetailResponse
{
    private int cartDetailId;
    private int id;
    private String title;
    private byte[] image;
    private int amount;
    private int userId;

    public CartDetailResponse() 
    {

    } 
    public CartDetailResponse(int cartDetailId,int id, String title, byte[] image, int amount, int userId) 
    {
        this.cartDetailId= cartDetailId;
        this.id=id;
        this.title=title;
        this.image=image; 
        this.amount=amount; 
        this.userId= userId;
    } 
    public CartDetailResponse(CartDetail cartDetail) 
    {
        this.cartDetailId=cartDetail.getId();
        this.id=cartDetail.getBookTitleImagePath().getId();
        this.title= cartDetail.getBookTitleImagePath().getName();
        BookTitleImageData bookTitleImageData= new BookTitleImageData(cartDetail.getBookTitleImagePath());
        this.image=bookTitleImageData.getImageData(); 
        this.amount= cartDetail.getAmount(); 
        this.userId= cartDetail.getUser().getUserId();
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
    public int getCartDetailId() 
    {
        return this.cartDetailId;
    } 
    public int getAmount() 
    {
        return this.amount;
    } 
    public int getUserId() 
    {
        return this.userId;
    }
    
}
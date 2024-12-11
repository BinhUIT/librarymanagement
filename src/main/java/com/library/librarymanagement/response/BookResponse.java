package com.library.librarymanagement.response;

import com.library.librarymanagement.entity.BookImagePath;
import com.library.librarymanagement.entity.BookStatus;
import com.library.librarymanagement.entity.BookTitleImageData;
import com.library.librarymanagement.entity.BookTitleImagePath;

public class BookResponse { 
    private int id;
    private BookStatus status;
    private BookTitleImageData bookTitle;
    private boolean usable;

    public BookResponse() 
    {

    } 
    public BookResponse(int id, BookStatus status, BookTitleImageData bookTitle, boolean usable) 
    {
        this.id=id;
        this.status=status;
        this.bookTitle=bookTitle;
        this.usable=usable;
    } 
    public BookResponse(BookImagePath bookImagePath) 
    {
        this.id=bookImagePath.getId();
        this.status = bookImagePath.getStatus();
        this.bookTitle= new BookTitleImageData(bookImagePath.getTitle());
        this.usable= bookImagePath.getIsUsable();
    } 

    public int getId() 
    {
        return this.id;
    } 
    public BookStatus getStatus()
    {
        return this.status;
    } 
    public BookTitleImageData getBookTitle() 
    {
        return this.bookTitle;
    } 
    public boolean getUsable() 
    {
        return this.usable;
    }
    
}

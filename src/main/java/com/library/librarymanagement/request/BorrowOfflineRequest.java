package com.library.librarymanagement.request;

import java.util.List;

public class BorrowOfflineRequest { 
    private int userId;
    private List<Integer> listBook; 

    public BorrowOfflineRequest() 
    {

    } 
    public BorrowOfflineRequest(int userId, List<Integer> listBook) 
    {
        this.userId= userId;
        this.listBook= listBook;
    }

    public int getUserId() 
    {
        return this.userId;
    } 
    public List<Integer> getListBook() 
    {
        return this.listBook;
    }

}

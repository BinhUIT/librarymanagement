package com.library.librarymanagement.request;

import java.util.Date;

public class AddWorkDetail {
    private int librarianId;
    private Date workDate;

    public AddWorkDetail() 
    {

    } 
    public AddWorkDetail(int librarianId, Date workDate) 
    {
        this.librarianId= librarianId;
        this.workDate= workDate;
    } 

    public int getLibrarianId() 
    {
        return this.librarianId;
    } 
    public Date getWorkDate(){
        return this.workDate;
    }
} 

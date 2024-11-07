package com.library.librarymanagement.request;

public class LibrarianResponse { 
    private boolean isAccept; 
    private int requestId; 
    private int librarianId;

    public LibrarianResponse() 
    {

    } 

    public LibrarianResponse(boolean isAccept, int requestId, int librarianId) 
    { 
        this.isAccept=isAccept; 
        this.requestId=requestId;
        this.librarianId=librarianId;
    } 
    public boolean getIsAccept() 
    {
        return this.isAccept;
    } 
    public int getRequestId() 
    {
        return this.requestId;
    } 
    public int getLibrarianId() 
    {
        return this.librarianId;
    }
}

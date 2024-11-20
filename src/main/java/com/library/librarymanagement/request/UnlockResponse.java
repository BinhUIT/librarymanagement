package com.library.librarymanagement.request;

public class UnlockResponse { 
    private int unlockRequestId; 
    private boolean response; 
    public UnlockResponse() 
    {

    } 
    public UnlockResponse(int unlockRequestId, boolean response) 
    {  
        this.unlockRequestId= unlockRequestId; 
        this.response= response;
    } 
    public int getUnlockRequestId() 
    { 
        return this.unlockRequestId; 
    } 
    public boolean getResponse() 
    { 
        return this.response;
    }

}

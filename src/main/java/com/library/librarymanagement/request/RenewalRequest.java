package com.library.librarymanagement.request;

import java.util.Date;


public class RenewalRequest { 
    private Date newExpireDate;

    public RenewalRequest() 
    {

    } 
    public RenewalRequest(Date newExpireDate) 
    {
        this.newExpireDate= newExpireDate;
    } 
    public Date getNewExpireDate() 
    {
        return this.newExpireDate;
    }
}

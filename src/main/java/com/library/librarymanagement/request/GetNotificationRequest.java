package com.library.librarymanagement.request;

public class GetNotificationRequest { 
    private int notifId; 
    public GetNotificationRequest() 
    {

    }

    public GetNotificationRequest(int notifId) 
    {
        this.notifId = notifId;
    } 
    public int getNotifId() 
    {
        return this.notifId;
    }

}
 
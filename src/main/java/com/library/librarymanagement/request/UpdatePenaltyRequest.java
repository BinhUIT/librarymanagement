package com.library.librarymanagement.request;

public class UpdatePenaltyRequest {
    private String content;
    public UpdatePenaltyRequest() {

    } 
    public UpdatePenaltyRequest(String content) 
    {
        this.content= content;
    } 
    public String getContent() 
    {
        return this.content;
    }

}

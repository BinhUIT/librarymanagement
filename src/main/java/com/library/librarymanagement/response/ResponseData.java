package com.library.librarymanagement.response;

public class ResponseData { 
    private String message;
    private Object data;
    public ResponseData() 
    { 

    } 
    public ResponseData(String message, Object data) 
    { 
        this.message=message;
        this.data=data;
    } 
    public String getMessage() 
    { 
        return this.message;
    } 
    public Object getData() 
    { 
        return this.data;
    }

}

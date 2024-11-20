package com.library.librarymanagement.request;

import java.util.List;



public class ReturningRequest {  
    private ServiceRequest serviceRequest;
    private List<ReturningDetailRequest> listRequest; 

    public ReturningRequest() 
    { 

    } 
    public ReturningRequest(ServiceRequest serviceRequest, List<ReturningDetailRequest> listRequest) 
    { 
        this.serviceRequest= serviceRequest; 
        this.listRequest = listRequest;
    } 

    public ServiceRequest getServiceRequest() 
    {
        return this.serviceRequest;
    } 
    public List<ReturningDetailRequest> getListRequest() 
    { 
        return this.listRequest;
    }


}

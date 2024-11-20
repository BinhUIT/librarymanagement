package com.library.librarymanagement.request;

import java.util.List;

public class RenewalRequest { 
    private ServiceRequest serviceRequest; 
    private List<RenewalCardDetailRequest> listRenewalDetails; 

    public RenewalRequest() 
    { 

    } 

    public RenewalRequest(ServiceRequest serviceRequest, List<RenewalCardDetailRequest> listRenewalDetails) 
    { 
        this.serviceRequest = serviceRequest; 
        this.listRenewalDetails= listRenewalDetails; 
    } 

    public ServiceRequest getServiceRequest() 
    { 
        return this.serviceRequest; 
    } 
    public List<RenewalCardDetailRequest> getListRenewalDetails() 
    {
        return this.listRenewalDetails;
    }
}

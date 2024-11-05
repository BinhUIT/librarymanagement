package com.library.librarymanagement.request;
 
import java.util.List;

public class BorrowingRequest { 
    private ServiceRequest serviceRequest;
    private List<BorrowingDetailRequest> listBook;
    public BorrowingRequest() 
    {

    } 
    public BorrowingRequest(ServiceRequest serviceRequest, List<BorrowingDetailRequest> listBook) 
    {
        this.serviceRequest=serviceRequest; 
        this.listBook=listBook;

    } 
    public ServiceRequest getServiceRequest() 
    { 
        return this.serviceRequest;
    } 
    public List<BorrowingDetailRequest> getListBook() 
    {
        return this.listBook;
    }

}

package com.library.librarymanagement.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants;

@Entity 
@FieldNameConstants 
@Table(name="SERVICE")
public class Service { 
    @Id 
    @Column(name="SERVICEID", columnDefinition="INT") 
    private int serviceId; 


    @ManyToOne 
    @JoinColumn(name="READERID", columnDefinition="INT", referencedColumnName="USERID") 
    private User reader; 

    @Column(name="IMPLEMENTDATE", columnDefinition="DATE") 
    private Date implementDate; 

    @ManyToOne 
    @JoinColumn(name="SERVICETYPEID", columnDefinition="INT", referencedColumnName="SERVICETYPEID") 
    private ServiceType serviceType;  

    @Column(name="REMINDTAKE", columnDefinition = "BOOLEAN") 
    private boolean remindTake;
    @Column(name="REMINDRETURN", columnDefinition = "BOOLEAN") 
    private boolean remindReturn;
    public Service() 
    {

    } 

    public Service(int serviceId, User reader, Date implementDate, ServiceType serviceType) 
    {
        this.serviceId=serviceId; 
        this.reader=reader; 
        this.implementDate=implementDate; 
        this.serviceType=serviceType;  
        this.remindTake=false;
        this.remindReturn=false;
    }  
    public Service(int serviceId, User reader, Date implementDate, ServiceType serviceType, boolean remindTake,boolean remindReturn) 
    {
        this.serviceId=serviceId; 
        this.reader=reader; 
        this.implementDate=implementDate; 
        this.serviceType=serviceType;  
        this.remindTake=remindTake;
        this.remindReturn=remindReturn;
    } 
    public boolean getRemindTake() 
    {
        return this.remindTake;
    } 
    public boolean getRemindReturn() 
    {
        return this.remindReturn;
    } 
    public void setRemindTake(boolean remindTake)
    {
        this.remindTake= remindTake;
    } 
    public void setRemindReturn(boolean remindReturn) 
    {
        this.remindReturn= remindReturn;
    }
    public int getServiceId() 
    {
        return this.serviceId;
    }  
    public User getReader()  
    {
        return this.reader;
    } 

    public Date getImplementDate() 
    {
        return this.implementDate;
    } 
    public ServiceType getServiceType() 
    { 
        return this.serviceType;
    } 

    public void setServiceId(int serviceId) 
    { 
        this.serviceId=serviceId;
    }   
    public void setReader(User reader) 
    {
        this.reader=reader;
    } 
    public void setImplementDate(Date implementDate) 
    { 
        this.implementDate=implementDate;
    } 
    public void setServieType(ServiceType serviceType) 
    {
        this.serviceType=serviceType;
    }


}

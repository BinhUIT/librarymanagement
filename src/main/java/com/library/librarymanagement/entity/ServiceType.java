package com.library.librarymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants;

@Entity 
@FieldNameConstants 
@Table(name="SERVICETYPE")
public class ServiceType { 
    @Id 
    @Column(name="SERVICETYPEID", columnDefinition="INT") 
    private int serviceTypeId; 

    @Column(name="NAME", columnDefinition="VARCHAR(45)") 
    private String name; 


    public ServiceType() 
    {

    } 
    public ServiceType(int serviceTypeId, String name) 
    {
        this.serviceTypeId=serviceTypeId;
        this.name=name;
    } 

    public int getServiceTypeId()
    {
        return this.serviceTypeId;
    } 
    public String getServiceTypeName() 
    {  
        return this.name;
    }

    public void setServiceTypeId(int serviceTypeId) 
    { 
        this.serviceTypeId=serviceTypeId;
    } 
    public void setName(String name) 
    { 
        this.name=name;
    }

}

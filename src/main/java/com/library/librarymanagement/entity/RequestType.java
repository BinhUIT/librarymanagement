package com.library.librarymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants;

@Entity 
@FieldNameConstants 
@Table(name="REQUESTTYPE")
public class RequestType { 
    @Id 
    @Column(name="REQUESTTYPEID", columnDefinition="INT NOT NULL", nullable =false) 
    private int requestTypeId; 

    @Column(name="NAME", columnDefinition="VARCHAR(45)") 
    private String name; 

    public RequestType() 
    {

    } 
    public RequestType(int requestTypeId, String name) 
    { 
        this.requestTypeId=requestTypeId;
        this.name=name;
    }  

    public int getRequestTypeId() 
    { 
        return this.requestTypeId;
    } 
    public void setRequestTypeId(int requestTypeId) 
    {
        this.requestTypeId=requestTypeId;
    } 

    public String getName() 
    {
        return this.name;
    } 
    public void setName(String name) 
    {
        this.name=name;
    }
}

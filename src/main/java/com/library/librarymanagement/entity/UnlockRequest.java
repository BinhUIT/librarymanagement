package com.library.librarymanagement.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants;

@Entity 
@FieldNameConstants
@Table(name="UNLOCKREQUEST")
public class UnlockRequest { 
    @Id 
    @Column(name="ID", columnDefinition = "INT") 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id; 
    
    @ManyToOne 
    @JoinColumn(name="READERID", columnDefinition = "INT", referencedColumnName = "USERID") 
    private User reader;

    @Column(name="RESPONSE", columnDefinition = "BOOLEAN") 
    private boolean response; 

    @ManyToOne 
    @JoinColumn(name="LIBRARIANID", columnDefinition = "INT", referencedColumnName = "USERID") 
    private User librarian; 

    @Column(name="SENDDATE", columnDefinition = "DATE") 
    private Date sendDate; 

    public UnlockRequest() 
    {

    } 

    public UnlockRequest(int id, User reader, boolean response, User librarian, Date sendDate) 
    { 
        this.id=id; 
        this.reader=reader; 
        this.response= response; 
        this.librarian=librarian; 
        this.sendDate=sendDate;
    } 

    public int getId() 
    { 
        return this.id;
    } 
    public User getReader() 
    {
        return this.reader;
    } 
    public boolean getResponse() 
    {
        return this.response;
    } 
    public void setReponse(boolean response) 
    { 
        this.response=response;
    } 

    public User getLibrarian() 
    { 
        return this.librarian;
    } 

    public void setLibrarian(User librarian) 
    {  
        this.librarian=librarian;
    } 
    public Date getSendDate() 
    { 
        return this.sendDate;
    }

}

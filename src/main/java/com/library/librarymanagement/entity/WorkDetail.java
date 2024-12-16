package com.library.librarymanagement.entity;

import java.util.Date;

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
@Table(name="WORKDETAIL")
public class WorkDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", columnDefinition="INT") 
    private int id; 

    @ManyToOne
    @JoinColumn(name="LIBRARIANID", columnDefinition = "INT", referencedColumnName = "USERID") 
    private User librarian;

    @Column(name="WORKDATE", columnDefinition = "DATE") 
    private Date workDate;

    public WorkDetail() 
    {

    } 
    public WorkDetail(int id, User librarian, Date workDate) 
    {
        this.id=id;
        this.librarian=librarian; 
        this.workDate=workDate;
    } 

    public WorkDetail(User librarian, Date workDate) 
    {
        this.librarian=librarian;
        this.workDate=workDate;
    } 

    public int getId() 
    {
        return this.id;
    } 
    public User getLibrarian() 
    {
        return this.librarian;
    } 
    public Date getWorkDate() 
    {
        return this.workDate;
    } 
    public void setLibrarian(User librarian) 
    {
        this.librarian=librarian;
    } 
    public void setWorkDate(Date workDate) 
    {
        this.workDate= workDate;
    }

}

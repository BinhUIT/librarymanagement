package com.library.librarymanagement.entity;

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
@Table(name="PENALTY")
public class Penalty { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name="ID", columnDefinition = "INT") 
    private int id;

    @Column(name="CONTENT", columnDefinition = "VARCHAR(100)") 
    private String content;

    @ManyToOne
    @JoinColumn(name="READERID", columnDefinition = "INT", referencedColumnName = "USERID") 
    private User reader;

    @Column(name="MONEY", columnDefinition = "INT") 
    private int money; 

    public Penalty() 
    {

    } 
    public Penalty(int id, String content, User reader, int money) 
    {
        this.id=id;
        this.content=content;
        this.reader=reader;
        this.money= money;
    } 

    public Penalty(String content, User reader, int money) 
    {
        this.content=content;
        this.reader= reader;
        this.money= money;
    }

    public int getId() 
    {
        return this.id;
    } 
    public String getContent() 
    {
        return this.content;
    }
    public User getReader() 
    {
        return this.reader;
    } 
    public int getMoney() 
    {
        return this.money;
    } 
    public void setContent(String content) 
    {
        this.content= content;
    }
}

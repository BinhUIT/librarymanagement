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
@Table(name="NOTIFICATION")
public class Notification { 
    @Id 
    @Column(name="NOTIFID", columnDefinition="INT") 
    private int notifId; 

   @ManyToOne 
   @JoinColumn(name="READERID", columnDefinition="INT", referencedColumnName="USERID") 
   private User reader; 

   @Column(name="SENDINGDATE", columnDefinition="DATE") 
   private Date sendingDate;

   @Column(name="ISREAD", columnDefinition="BOOLEAN") 
   private boolean isRead; 

   @Column(name="SUBJECT", columnDefinition="VARCHAR(45)") 
   private String subject; 

   @Column(name="MESSAGE", columnDefinition="VARCHAR(100)") 
   private String message; 

   public Notification() 
   {

   } 
   public Notification(int notifId, User reader, Date sendingDate, boolean isRead, String subject, String message) 
   {
        this.notifId=notifId;
        this.reader=reader; 
        this.sendingDate= sendingDate; 
        this.isRead=isRead; 
        this.subject=subject; 
        this.message= message;
   }  


   public int getNotifId() 
   {
        return this.notifId;
   } 
   public User getReader() 
   {
        return this.reader;
   } 
   public Date getSendingDate() 
   {
        return this.sendingDate; 
   } 
   public boolean getIsRead() 
   { 
        return this.isRead;
   } 
   public String getSubject() 
   {
        return this.subject;
   } 
   public String getMessage() 
   {
        return this.message;
   } 
   public void setIsread(boolean isRead) 
   { 
        this.isRead=isRead;
   }
}

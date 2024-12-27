package com.library.librarymanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants;

@Entity 
@FieldNameConstants 
@Table(name="REGULATION")
public class Regulation { 
    @Id
    @Column(name="ID", columnDefinition="INT") 
    private int id; 

    @Column(name="DEFAULTBORROWINGDAYS", columnDefinition = "INT") 
    private int defaultBorrowingDays;

    @Column(name="MONEYLATEPERDAY", columnDefinition = "INT") 
    private int moneyLatePerDay;

    @Column(name="DAYSTOTAKEBOOK", columnDefinition = "INT") 
    private int daysToTakeBook; 

    @Column (name="DAYSTORESPONSERENEWAL", columnDefinition = "INT") 
    private int daysToResponseRenewal; 

    @Column(name="DAYSTOLOCKUSER", columnDefinition = "INT") 
    private int daysToLockUser;

    public Regulation() 
    {

    } 
    public Regulation(int id, int defaultBorrowingDays, int moneyLatePerDay, int daysToTakeBook, int daysToResponseRenewal, int daysToLockUser) 
    {
        this.id=id;
        this.defaultBorrowingDays= defaultBorrowingDays;
        this.moneyLatePerDay= moneyLatePerDay;
        this.daysToTakeBook= daysToTakeBook; 
        this.daysToResponseRenewal= daysToResponseRenewal; 
        this.daysToLockUser = daysToLockUser;
    } 

    public int getId() 
    {
        return this.id;
    } 
    public int getDefaultBorrowingDays() 
    {
        return this.defaultBorrowingDays;
    } 
    public int getMoneyLatePerDay() 
    {
        return this.moneyLatePerDay;
    } 
    public int getDaysToTakeBook() 
    {
        return this.daysToTakeBook;
    }

    public void setDefaultBorrowingDays(int defaultBorrowingDays) 
    {
        this.defaultBorrowingDays= defaultBorrowingDays;
    } 
    public void setMoneyLatePerDay(int moneyLatePerDay) 
    {
        this.moneyLatePerDay= moneyLatePerDay;
    } 
    public void setDaysToTakeBook(int daysToTakeBook) 
    {
        this.daysToTakeBook= daysToTakeBook;
    }
    public int getDaysToResponseRenewal() 
    {
        return this.daysToResponseRenewal;
    } 
    public void setDaysToResponseRenewal(int daysToResponseRenewal) 
    {
        this.daysToResponseRenewal= daysToResponseRenewal;
    } 
    public int getDaysToLockUser() 
    {
        return this.daysToLockUser;
    } 
    public void setDaysToLockUser(int daysToLockUser) 
    {
        this.daysToLockUser= daysToLockUser;
    }


    
}

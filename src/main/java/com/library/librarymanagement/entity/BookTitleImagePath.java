package com.library.librarymanagement.entity;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants;

@Entity
@FieldNameConstants
@Table(name = "BookTitle")
public final class BookTitleImagePath {
    @Id
    @Column(name = "Id", columnDefinition = "int", nullable = false, unique = true, insertable = false, updatable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;

    @Column(name = "Name", columnDefinition = "varchar(45)", nullable = false, unique = true)
    private String name = null;

    @Column(name = "Amount", columnDefinition = "int", nullable = false, insertable = true)
    @ColumnDefault(value = "0")
    private Integer amount = null;

    @Column(name = "AmountRemaining", columnDefinition = "int", nullable = false, insertable = true)
    @ColumnDefault(value = "0")
    private Integer amountRemaining = null;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TypeId", columnDefinition = "smallint", nullable = false, referencedColumnName = "id")
    private BookTypeImagePath type = null;

    @Column(name = "Author", columnDefinition = "varchar(45)", nullable = false)
    private String author = null;

    @Column(name = "ImagePath", columnDefinition = "varchar(45)", nullable = false, unique = true)
    private String imagePath = null; 

    @Column(name="NXB", columnDefinition = "varchar(45)") 
    private String nxb;

    @Column(name="YEAR", columnDefinition = "INT") 
    private int year;

    @Column(name="LANGUAGE", columnDefinition = "VARCHAR(45)") 
    private String language;

    @Column(name="PAGEAMOUNT", columnDefinition = "INT") 
    private int pageAmount;

    @Column(name="REVIEW", columnDefinition = "VARCHAR(1000)") 
    private String review;   

    @Column(name="ENABLE", columnDefinition = "BOOLEAN") 
    private boolean enable;

    @Autowired(required = true)
    private BookTitleImagePath() {
    }

    public BookTitleImagePath(final String name, final BookTypeImagePath type, final String author,
            final String imagePath) {
        this.name = name;
        this.type = type;
        this.author = author;
        this.imagePath = imagePath; 
        this.enable=true;
    } 
    public BookTitleImagePath(int id,String name, BookTypeImagePath type, String author, String imagePath, String nxb, int year, String language, int pageAmount, String review) 
    {
        this.id=id;
        this.name = name;
        this.type = type;
        this.author = author;
        this.imagePath = imagePath;
        this.nxb=nxb;
        this.year=year;
        this.language=language;
        this.pageAmount=pageAmount;
        this.review=review;
        this.amount=0;
        this.amountRemaining=0; 
        this.enable=true;
    }
    public BookTitleImagePath(int id, String name, BookTypeImagePath type, String author, String imagePath) 
    { 
        this.id=id;
        this.name = name;
        this.type = type;
        this.author = author;
        this.imagePath = imagePath; 
        this.amount=0; 
        this.amountRemaining=0; 
        this.enable=true;
    }
    public String getNxb() 
    {
        return this.nxb;
    } 
    public int getYear() 
    {
        return this.year;
    } 
    public String getLanguage() 
    {
        return this.language;
    } 
    public int getPageAmount() 
    {
        return this.pageAmount;
    } 
    public String getReview() 
    {
        return this.review;
    } 
    public void setNxb(String nxb) 
    {
        this.nxb=nxb;
    }
    public void setYear(int year) 
    {
        this.year=year;
    } 
    public void setLanguage(String language) 
    {
        this.language=language;
    } 
    public void setPageAmount(int pageAmount) 
    {
        this.pageAmount=pageAmount;
    } 
    public void setReview(String review) 
    {
        this.review=review;
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public Integer getAmountRemaining() {
        return this.amountRemaining;
    }

    public BookTypeImagePath getType() {
        return this.type;
    }

    public void setType(final BookTypeImagePath type) {
        this.type = type;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(final String imagePath) {
        this.imagePath = imagePath;
    } 
    public void setAmount(int amount) 
    { 
        this.amount=amount;
    } 
    public void setAmountRemaining(int amountRemaining) 
    { 
        this.amountRemaining=amountRemaining;
    } 

    public boolean getEnable() 
    {
        return this.enable;
    } 
    public void setEnable(boolean enable) 
    {
        this.enable=enable;
    }
}

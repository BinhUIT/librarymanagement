package com.library.librarymanagement.entity;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Autowired;

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
@Table(name = "BookTitle")
public final class BookTitleImagePath {
    @Id
    @Column(name = "Id", columnDefinition = "int", nullable = false, unique = true, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;

    @Column(name = "Name", columnDefinition = "varchar(45)", nullable = false, unique = true)
    private String name = null;

    @Column(name = "Amount", columnDefinition = "int", nullable = false, insertable = false, updatable = false)
    @ColumnDefault(value = "0")
    private Integer amount = null;

    @Column(name = "AmountRemaining", columnDefinition = "int", nullable = false, insertable = false, updatable = false)
    @ColumnDefault(value = "0")
    private Integer amountRemaining = null;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TypeId", columnDefinition = "smallint", nullable = false, referencedColumnName = "id")
    private BookTypeImagePath type = null;

    @Column(name = "Author", columnDefinition = "varchar(45)", nullable = false)
    private String author = null;

    @Column(name = "ImagePath", columnDefinition = "varchar(45)", nullable = false, unique = true)
    private String imagePath = null;

    @Autowired(required = true)
    private BookTitleImagePath() {
    }

    public BookTitleImagePath(final String name, final BookTypeImagePath type, final String author,
            final String imagePath) {
        this.name = name;
        this.type = type;
        this.author = author;
        this.imagePath = imagePath;
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

    public boolean setNameIfNotNull(final String name) {
        if (name != null) {
            this.name = name;
            return true;
        } else {
            return false;
        }
    }

    public Integer getAmount() {
        return this.amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    public Integer getAmountRemaining() {
        return this.amountRemaining;
    }

    public void setAmountRemaining(final Integer amountRemaining) {
        this.amountRemaining = amountRemaining;
    }

    public BookTypeImagePath getType() {
        return this.type;
    }

    public void setType(final BookTypeImagePath type) {
        this.type = type;
    }

    public boolean setTypeIfNotNull(final BookTypeImagePath type) {
        if (type != null) {
            this.type = type;
            return true;
        } else {
            return false;
        }
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public boolean setAuthorIfNotNull(final String author) {
        if (author != null) {
            this.author = author;
            return true;
        } else {
            return false;
        }
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(final String imagePath) {
        this.imagePath = imagePath;
    }
}

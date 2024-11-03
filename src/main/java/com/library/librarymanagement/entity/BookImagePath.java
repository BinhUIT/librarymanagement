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
@Table(name = "Book")
public final class BookImagePath {
    @Id
    @Column(name = "id", columnDefinition = "int", nullable = false, unique = true, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = null;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TitleId", columnDefinition = "int", nullable = false, referencedColumnName = "id")
    private BookTitleImagePath title = null;

    @ManyToOne(optional = false)
    @JoinColumn(name = "StatusId", columnDefinition = "tinyint", nullable = false, referencedColumnName = "id")
    private BookStatus status = null;

    @Column(name = "IsUsable", columnDefinition = "bit", nullable = false)
    @ColumnDefault(value = "TRUE")
    private boolean isUsable = true;

    @Autowired(required = true)
    private BookImagePath() {
    }

    public BookImagePath(final BookTitleImagePath title, final BookStatus status, final boolean isUsable) {
        this.title = title;
        this.status = status;
        this.isUsable = isUsable;
    }

    public Integer getId() {
        return id;
    }

    public BookTitleImagePath getTitle() {
        return this.title;
    }

    public void setTitle(final BookTitleImagePath title) {
        this.title = title;
    }

    public boolean setTitleIfNotNull(final BookTitleImagePath title) {
        if (title != null) {
            this.title = title;
            return true;
        } else {
            return false;
        }
    }

    public BookStatus getStatus() {
        return this.status;
    }

    public void setStatus(final BookStatus status) {
        this.status = status;
    }

    public boolean setStatusIfNotNull(final BookStatus status) {
        if (status != null) {
            this.status = status;
            return true;
        } else {
            return false;
        }
    }

    public boolean getIsUsable() {
        return this.isUsable;
    }

    public void setUsable(final boolean isUsable) {
        this.isUsable = isUsable;
    }

    public boolean setUsableIfNotNull(final Boolean isUsable) {
        if (isUsable != null) {
            this.isUsable = isUsable;
            return true;
        } else {
            return false;
        }
    }
}

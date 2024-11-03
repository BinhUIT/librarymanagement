package com.library.librarymanagement.entity;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.experimental.FieldNameConstants;

@Entity
@FieldNameConstants
@Table(name = "BookType")
public final class BookTypeImagePath {
    @Id
    @Column(name = "Id", columnDefinition = "smallint", nullable = false, unique = true, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id = null;

    @Column(name = "Name", columnDefinition = "varchar(45)", nullable = false, unique = true)
    private String name = null;

    @Column(name = "ImagePath", columnDefinition = "varchar(100)", nullable = false, unique = true)
    private String imagePath = null;

    @Autowired(required = true)
    private BookTypeImagePath() {
    }

    public BookTypeImagePath(final String name, final String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }

    public Short getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public boolean setNameIfNotBlank(final String name) {
        if ((name != null) && (!name.isBlank())) {
            this.name = name;
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

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
@Table(name = "BookStatus")
public final class BookStatus {
    @Id
    @Column(name = "id", columnDefinition = "tinyint", nullable = false, unique = true, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id = null;

    @Column(name = "name", columnDefinition = "varchar(45)", nullable = false, unique = true)
    private String name = null;

    @Autowired(required = true)
    private BookStatus() {
    }

    public BookStatus(final String name) {
        this.name = name;
    }

    public Byte getId() {
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
}

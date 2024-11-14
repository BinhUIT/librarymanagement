package com.library.librarymanagement.entity;

import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldNameConstants
@Table(name = "BookType")
public final class BookTypeImagePath {
    @Id
    @Column(name = "Id", nullable = false, unique = true, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Short id = null;

    @Nationalized
    @Column(name = "Name", length = 45, nullable = false, unique = true)
    private String name = null;

    @Nationalized
    @Column(name = "ImagePath", length = 100, nullable = false, unique = true)
    private String imagePath = null;

    public BookTypeImagePath(final String name, final String imagePath) {
        this.name = name;
        this.imagePath = imagePath;
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

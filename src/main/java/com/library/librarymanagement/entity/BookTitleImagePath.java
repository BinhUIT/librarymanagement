package com.library.librarymanagement.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Nationalized;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "BookTitle")
@DynamicInsert
public final class BookTitleImagePath {
    @Id
    @Column(name = "Id", nullable = false, unique = true, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Integer id = null;

    @Nationalized
    @Column(name = "Name", length = 45, nullable = false, unique = true)
    private String name = null;

    @Column(name = "Amount", nullable = false, insertable = false, updatable = false)
    private Integer amount = null;

    @Column(name = "AmountRemaining", nullable = false, insertable = false, updatable = false)
    private Integer amountRemaining = null;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TypeId", nullable = false)
    private BookTypeImagePath type = null;

    @Nationalized
    @Column(name = "Author", length = 45, nullable = false)
    private String author = null;

    @Nationalized
    @Column(name = "ImagePath", length = 100, nullable = false, unique = true)
    private String imagePath = null;

    public BookTitleImagePath(final String name, final BookTypeImagePath type, final String author,
            final String imagePath) {
        this.name = name;
        this.type = type;
        this.author = author;
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

    public boolean setTypeIfNotNull(final BookTypeImagePath type) {
        if (type != null) {
            this.type = type;
            return true;
        } else {
            return false;
        }
    }

    public boolean setAuthorIfNotBlank(final String author) {
        if ((author != null) && (!author.isBlank())) {
            this.author = author;
            return true;
        } else {
            return false;
        }
    }
}

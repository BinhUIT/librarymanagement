package com.library.librarymanagement.entity;

import org.hibernate.annotations.ColumnDefault;

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
@Table(name = "Book")
public final class BookImagePath {
    @Id
    @Column(name = "id", nullable = false, unique = true, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Integer id = null;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TitleId", nullable = false)
    private BookTitleImagePath title = null;

    @ManyToOne(optional = false)
    @JoinColumn(name = "StatusId", nullable = false)
    private BookStatus status = null;

    @Column(name = "IsUsable", nullable = false)
    @ColumnDefault(value = "TRUE")
    private boolean isUsable = true;

    public BookImagePath(final BookTitleImagePath title, final BookStatus status, final boolean isUsable) {
        this.title = title;
        this.status = status;
        this.isUsable = isUsable;
    }

    public boolean setTitleIfNotNull(final BookTitleImagePath title) {
        if (title != null) {
            this.title = title;
            return true;
        } else {
            return false;
        }
    }

    public boolean setStatusIfNotNull(final BookStatus status) {
        if (status != null) {
            this.status = status;
            return true;
        } else {
            return false;
        }
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
